package misk.db.feature

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.util.concurrent.AbstractIdleService
import misk.db.FeatureQueries
import misk.db.Features
import misk.db.protos.feature.FeatureConfig
import misk.db.protos.feature.FeatureMetadata
import misk.db.protos.feature.FeatureRule
import misk.feature.Attributes
import misk.feature.DynamicConfig
import misk.feature.Feature
import misk.feature.FeatureFlags
import misk.feature.FeatureService
import misk.feature.TrackerReference
import wisp.feature.BooleanFeatureFlag
import wisp.feature.DoubleFeatureFlag
import wisp.feature.EnumFeatureFlag
import wisp.feature.IntFeatureFlag
import wisp.feature.JsonFeatureFlag
import wisp.feature.StringFeatureFlag
import wisp.feature.fromSafeJson
import wisp.logging.getLogger
import wisp.moshi.defaultKotlinMoshi
import java.time.Clock
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Database-backed implementation of [FeatureFlags] that relies on
 *  SqlDelight generated [FeatureQueries] for persistence of flag values
 *  and uses in-memory Guava Cache for improved read performance.
 */
@Singleton
class DbFeatureFlags @Inject constructor(
  private val queries: FeatureQueries,
  private val clock: Clock,
  private val defaults: List<DbFeatureFlagsModule.DbFeatureFlagsDefaultModule.DbFeatureFlagsDefault>,
  memoryCacheExpireAfterWriteDuration: Duration = 60.seconds,
  memoryCacheSize: Long = 1000,
) : AbstractIdleService(),
  FeatureFlags,
  FeatureService,
  DynamicConfig {
  companion object {
    private val logger = getLogger<DbFeatureFlags>()

    const val KEY = "fake_dynamic_flag"
    val defaultAttributes = Attributes()

    /** Trackers setup. */
    private val trackers = HashMap<MapKey, MutableList<TrackerMapValue<*>>>()

    private data class MapKey(
      val feature: wisp.feature.Feature,
      val key: String = KEY
    )

    /**
     * Data class that holds a tracker for the given [feature, key, attributes].
     */
    private data class TrackerMapValue<T>(
      val attributes: wisp.feature.Attributes = defaultAttributes,
      val executor: Executor,
      val tracker: (T) -> Unit
    )

    /** Choose the winning rule for a Feature config. */
    private fun List<FeatureRule>.evaluate() = first()

    /**
     * Evaluate rules and return a value for the flag.
     *
     * TODO support multiple rules, key, attributes instead of only DynamicConfig.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> FeatureConfig.evaluate(
      clazz: Class<T>,
      key: String,
      attributes: wisp.feature.Attributes
    ): T {
      val r = rules.evaluate()
      return when {
        clazz == java.lang.Boolean::class.java -> r.value_boolean!! as T
        clazz == java.lang.Double::class.java -> r.value_double!! as T
        clazz == java.lang.Integer::class.java -> r.value_int!! as T
        clazz == java.lang.String::class.java -> r.value_string!! as T
        clazz.superclass == java.lang.Enum::class.java ->
          java.lang.Enum.valueOf(clazz as Class<out Enum<*>>, r.value_enum!!.uppercase()) as T

        (clazz as Class<Any>).kotlin.isData -> {
          return defaultKotlinMoshi.adapter(clazz).fromSafeJson(r.value_json!!) as T
            ?: throw IllegalArgumentException("null value deserialized for JSON [type=$clazz]")
        }

        else -> throw IllegalArgumentException("Unsupported requested feature flag [type=$clazz]")
      }
    }

    /**
     * Evaluate rules and return a value for the flag in its proto serialized form.
     *
     * For example, for JSON this returns the raw JSON as string instead of the JSON Class instance.
     *
     * TODO support multiple rules, key, attributes instead of only DynamicConfig.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> FeatureConfig.evaluateSerialized(
      clazz: Class<T>,
      key: String,
      attributes: wisp.feature.Attributes
    ): T {
      val r = rules.evaluate()
      return when {
        clazz == java.lang.Boolean::class.java -> r.value_boolean!! as T
        clazz == java.lang.Double::class.java -> r.value_double!! as T
        clazz == java.lang.Integer::class.java -> r.value_int!! as T
        clazz == java.lang.String::class.java -> r.value_string!! as T
        clazz.superclass == java.lang.Enum::class.java ->
          java.lang.Enum.valueOf(clazz as Class<out Enum<*>>, r.value_enum!!.uppercase()) as T

        (clazz as Class<Any>).kotlin.isData -> r.value_json!! as T
        else -> throw IllegalArgumentException("Unsupported requested feature flag [type=$clazz]")
      }
    }

    inline fun <reified T> FeatureConfig.evaluate(key: String, attributes: wisp.feature.Attributes): T =
      this.evaluate(T::class.java, key, attributes)
  }

  private val cache = CacheBuilder.newBuilder()
    .maximumSize(memoryCacheSize)
    // Service may have multiple replicas/pods which won't receive the memoryCacheInvalidate call on flag change
    .expireAfterWrite(memoryCacheExpireAfterWriteDuration.inWholeSeconds, TimeUnit.SECONDS)
    .build(object : CacheLoader<String, Features>() {
      override fun load(key: String): Features =
        checkNotNull(queries.get(key).executeAsOneOrNull()) {
          "Unexpected Feature(key=$key) missing in database"
        }
    })

  /** On DB write to a feature key, invalidate the cache entry and populate with the new value. */
  internal fun memoryCacheInvalidate(key: String) {
    cache.invalidate(key)
  }

  override fun startUp() {
    defaults.forEach {
      it.defaultLambda(this)
    }
  }

  override fun shutDown() {}

  override fun get(flag: BooleanFeatureFlag): Boolean = cache.get(flag.feature.name)
    .metadata.config!!.evaluate(flag.key, flag.attributes)

  override fun get(flag: DoubleFeatureFlag): Double = cache.get(flag.feature.name)
    .metadata.config!!.evaluate(flag.key, flag.attributes)

  override fun get(flag: IntFeatureFlag): Int = cache.get(flag.feature.name)
    .metadata.config!!.evaluate(flag.key, flag.attributes)

  override fun get(flag: StringFeatureFlag): String = cache.get(flag.feature.name)
    .metadata.config!!.evaluate(flag.key, flag.attributes)

  override fun <T : Enum<T>> get(flag: EnumFeatureFlag<T>): T = cache.get(flag.feature.name)
    .metadata.config!!.evaluate(flag.returnType, flag.key, flag.attributes)

  override fun <T : Any> get(flag: JsonFeatureFlag<T>): T = cache.get(flag.feature.name)
    .metadata.config!!.evaluate(flag.returnType, flag.key, flag.attributes)

  private inline fun <reified T> getAny(feature: Feature, key: String, attributes: Attributes): T? =
    cache.get(feature.name).metadata.config!!.evaluate(key, attributes)

  override fun getBoolean(feature: Feature, key: String, attributes: Attributes): Boolean =
    getAny(feature, key, attributes) ?: false

  override fun getDouble(feature: Feature, key: String, attributes: Attributes): Double =
    getAny(feature, key, attributes) ?: 0.0

  override fun getInt(feature: Feature, key: String, attributes: Attributes): Int =
    getAny(feature, key, attributes) ?: 0

  override fun getString(feature: Feature, key: String, attributes: Attributes): String =
    getAny(feature, key, attributes) ?: ""

  override fun <T : Enum<T>> getEnum(
    feature: Feature,
    key: String,
    clazz: Class<T>,
    attributes: Attributes
  ): T = cache.get(feature.name).metadata.config!!.evaluate(clazz, key, attributes)

  override fun <T> getJson(
    feature: Feature,
    key: String,
    clazz: Class<T>,
    attributes: Attributes
  ): T = cache.get(feature.name).metadata.config!!.evaluate(clazz, key, attributes)

  override fun getBoolean(feature: Feature) = getBoolean(feature, KEY)
  override fun getDouble(feature: Feature) = getDouble(feature, KEY)
  override fun getInt(feature: Feature) = getInt(feature, KEY)
  override fun getString(feature: Feature) = getString(feature, KEY)
  override fun <T : Enum<T>> getEnum(feature: Feature, clazz: Class<T>): T =
    getEnum(feature, KEY, clazz)

  override fun <T> getJson(feature: Feature, clazz: Class<T>): T =
    getJson(feature, KEY, clazz)

  private fun <T> trackAny(
    feature: Feature,
    key: String,
    attributes: Attributes,
    executor: Executor,
    tracker: (T) -> Unit
  ): TrackerReference = synchronized(trackers) {
    val bucket = trackers
      .computeIfAbsent(MapKey(feature, key)) { mutableListOf() }
    val value = TrackerMapValue(attributes, executor, tracker)
    bucket.add(value)
    return object : TrackerReference {
      override fun unregister() {
        bucket.remove(value)
      }
    }
  }

  override fun trackBoolean(
    feature: Feature,
    key: String,
    attributes: Attributes,
    executor: Executor,
    tracker: (Boolean) -> Unit
  ) = trackAny(feature, key, attributes, executor, tracker)

  override fun trackDouble(
    feature: Feature,
    key: String,
    attributes: Attributes,
    executor: Executor,
    tracker: (Double) -> Unit
  ) = trackAny(feature, key, attributes, executor, tracker)

  override fun trackInt(
    feature: Feature,
    key: String,
    attributes: Attributes,
    executor: Executor,
    tracker: (Int) -> Unit
  ) = trackAny(feature, key, attributes, executor, tracker)

  override fun trackString(
    feature: Feature,
    key: String,
    attributes: Attributes,
    executor: Executor,
    tracker: (String) -> Unit
  ) = trackAny(feature, key, attributes, executor, tracker)

  override fun <T : Enum<T>> trackEnum(
    feature: Feature,
    key: String,
    clazz: Class<T>,
    attributes: Attributes,
    executor: Executor,
    tracker: (T) -> Unit
  ) = trackAny(feature, key, attributes, executor, tracker)

  override fun <T> trackJson(
    feature: Feature,
    key: String,
    clazz: Class<T>,
    attributes: Attributes,
    executor: Executor,
    tracker: (T) -> Unit
  ) = trackAny(feature, key, attributes, executor, tracker)

  override fun trackBoolean(
    feature: Feature,
    executor: Executor,
    tracker: (Boolean) -> Unit
  ) = trackBoolean(feature, KEY, executor, tracker)

  override fun trackDouble(
    feature: Feature,
    executor: Executor,
    tracker: (Double) -> Unit
  ) = trackDouble(feature, KEY, executor, tracker)

  override fun trackInt(
    feature: Feature,
    executor: Executor,
    tracker: (Int) -> Unit
  ) = trackInt(feature, KEY, executor, tracker)

  override fun trackString(
    feature: Feature,
    executor: Executor,
    tracker: (String) -> Unit
  ) = trackString(feature, KEY, executor, tracker)

  override fun <T : Enum<T>> trackEnum(
    feature: Feature,
    clazz: Class<T>,
    executor: Executor,
    tracker: (T) -> Unit
  ) = trackEnum(feature, KEY, clazz, executor, tracker)

  override fun <T> trackJson(
    feature: Feature,
    clazz: Class<T>,
    executor: Executor,
    tracker: (T) -> Unit
  ) = trackJson(feature, KEY, clazz, executor, tracker)

  private fun putIfAbsent(feature: Feature, rule: FeatureRule) {
    queries.get(feature.name).executeAsOneOrNull() ?: run {
      logger.warn("Expected flag not present, inserting default [name=${feature.name}][rule=$rule]")
      queries.insert(
        created_at = clock.instant(),
        updated_at = clock.instant(),
        name = feature.name,
        metadata = FeatureMetadata(
          config = FeatureConfig(
            rules = listOf(rule)
          )
        )
      )
    }
  }

  fun default(
    feature: Feature,
    value: Boolean
  ) = putIfAbsent(feature, FeatureRule(value_boolean = value))

  fun default(
    feature: Feature,
    value: Double
  ) = putIfAbsent(feature, FeatureRule(value_double = value))

  fun default(
    feature: Feature,
    value: Int
  ) = putIfAbsent(feature, FeatureRule(value_int = value))

  fun default(
    feature: Feature,
    value: String
  ) = putIfAbsent(feature, FeatureRule(value_string = value))

  fun default(
    feature: Feature,
    value: Enum<*>
  ) = putIfAbsent(feature, FeatureRule(value_enum = value.name.uppercase(), type_java_class_name = value.javaClass.name))

  fun defaultJsonString(
    feature: Feature,
    value: String,
    clazz: Class<*>
  ) = putIfAbsent(feature, FeatureRule(value_json = value, type_java_class_name = clazz.name))

  fun <T> defaultJson(
    feature: Feature,
    value: T,
    clazz: Class<T>
  ) = defaultJsonString(feature, defaultKotlinMoshi.adapter(clazz).toJson(value), clazz)

  inline fun <reified T> defaultJson(feature: Feature, value: T) {
    defaultJson(feature, value, T::class.java)
  }
}
