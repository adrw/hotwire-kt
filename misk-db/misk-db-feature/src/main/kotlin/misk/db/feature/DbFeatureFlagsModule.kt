package misk.db.feature

import app.cash.sqldelight.Query
import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.google.inject.Provider
import com.google.inject.Provides
import misk.ServiceModule
import misk.db.FeatureDatabase
import misk.db.FeatureQueries
import misk.db.feature.api.CreateOrUpdateFeatureAction
import misk.db.feature.api.DeleteFeatureAction
import misk.db.feature.api.GetFeaturesAction
import misk.db.feature.web.actions.frames.CreateFormAction
import misk.db.feature.web.actions.frames.CreateFormChooseFeatureTypeAction
import misk.db.feature.web.actions.frames.CreateFormExpandFeatureTypeAction
import misk.db.feature.web.actions.frames.FieldCreateValueBooleanAction
import misk.db.feature.web.actions.pages.CreateAction
import misk.db.feature.web.actions.pages.DetailsAction
import misk.db.feature.web.actions.pages.IndexAction
import misk.feature.DynamicConfig
import misk.feature.FeatureFlags
import misk.feature.FeatureService
import misk.inject.KAbstractModule
import misk.inject.asSingleton
import misk.inject.toKey
import misk.jdbc.DataSourceService
import misk.web.WebActionModule
import misk.web.dashboard.AdminDashboard
import misk.web.dashboard.AdminDashboardAccess
import misk.web.dashboard.DashboardTab
import misk.web.dashboard.DashboardTabProvider
import misk.web.dashboard.WebTabResourceModule
import java.sql.Connection
import java.time.Clock
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource
import kotlin.reflect.KClass

/**
 * Binds a [DbFeatureFlags] that uses a local DB to back FeatureFlags.
 *
 * In a given misk service's test setup, there is one [DbFeatureFlagsModule] installed.
 */
class DbFeatureFlagsModule(
  private val isDevelopment: Boolean,
  private val qualifier: KClass<out Annotation>? = null,
) : KAbstractModule() {
  override fun configure() {
    requireBinding<FeatureQueries>()

    // TODO add boot check that all keys present in DB


    // Setup DbFeatureFlags keys
    val key = DbFeatureFlags::class.toKey(qualifier)
    bind(key).toProvider(
      object : Provider<DbFeatureFlags> {
        @Inject lateinit var clock: Clock
        @Inject lateinit var defaults: List<DbFeatureFlagsDefaultModule.DbFeatureFlagsDefault>
        @Inject lateinit var queries: FeatureQueries
        override fun get(): DbFeatureFlags =
          DbFeatureFlags(queries, clock, defaults)
      }
    ).asSingleton()

    // Use DbFeatureFlags to back the following interfaces
    bind(FeatureFlags::class.toKey(qualifier)).to(key)
    // TODO add wisp FeatureFlags binding
    //    bind(wisp.feature.FeatureFlags::class.toKey(qualifier)).to(key)
    bind(FeatureService::class.toKey(qualifier)).to(key)
    bind(DynamicConfig::class.toKey(qualifier)).to(key)
    install(ServiceModule<FeatureService>(qualifier)
      .dependsOn<DataSourceService>(FeatureDb::class))

    // Install defaults modules
    newMultibinder<DbFeatureFlagsDefaultModule.DbFeatureFlagsDefault>()
    defaults.forEach { install(it) }

    // Setup gRPC actions for api and admin dashboard
    install(WebActionModule.create<CreateOrUpdateFeatureAction>())
    install(WebActionModule.create<GetFeaturesAction>())
    install(WebActionModule.create<DeleteFeatureAction>())

    // Install admin dashboard tab backing web actions
    // Pages
    install(WebActionModule.create<CreateAction>())
    install(WebActionModule.create<DetailsAction>())
    install(WebActionModule.create<IndexAction>())
    // Frames
    install(WebActionModule.create<CreateFormAction>())
    install(WebActionModule.create<CreateFormChooseFeatureTypeAction>())
    install(WebActionModule.create<CreateFormExpandFeatureTypeAction>())
    install(WebActionModule.create<FieldCreateValueBooleanAction>())
    // Admin dashboard tab
    multibind<DashboardTab>().toProvider(
      DashboardTabProvider<AdminDashboard, AdminDashboardAccess>(
        slug = "feature",
        url_path_prefix = "/_admin/feature/",
        name = "Feature",
        category = "Container Admin"
      )
    )
    install(
      WebTabResourceModule(
        isDevelopment = isDevelopment,
        slug = "feature",
        web_proxy_url = "http://localhost:3160/"
      )
    )
  }

  /**
   * Install defaults for [DbFeatureFlags]. This module can be install many times, allowing for
   * feature flag overrides to be modular and scoped to the module the flag is used in.
   *
   * In any module use:
   * ```
   * install(DbFeatureFlagsDefaultModule {
   *   default(Feature("foo"), true)
   *   defaultJsonString(Feature("bar"), "{ \"target\": 0.1 }")
   * })
   * ```
   */
  class DbFeatureFlagsDefaultModule private constructor(
    private val qualifier: KClass<out Annotation>? = null,
    private val default: DbFeatureFlagsDefault,
  ) : KAbstractModule() {

    constructor(
      qualifier: KClass<out Annotation>? = null,
      defaultLambda: DbFeatureFlags.() -> Unit,
    ) : this(
      qualifier,
      DbFeatureFlagsDefault(defaultLambda)
    )

    override fun configure() {
      multibind<DbFeatureFlagsDefault>(qualifier).toInstance(default)
    }

    class DbFeatureFlagsDefault(
      val defaultLambda: DbFeatureFlags.() -> Unit
    )
  }

  private val defaults = mutableListOf<DbFeatureFlagsDefaultModule>()

  /**
   * Add defaults for new feature flags set at service boot if not present in the database.
   *
   * Usage:
   * ```
   * install(DbFeatureFlagsModule().withDefaults {
   *   default(Feature("foo"), true)
   * })
   * ```
   *
   * For setting defaults in many modules see [DbFeatureFlagsDefaultModule]
   */
  fun withDefaults(lambda: DbFeatureFlags.() -> Unit): DbFeatureFlagsModule {
    defaults.add(DbFeatureFlagsDefaultModule(qualifier, lambda))
    return this
  }


  @Provides
  @Singleton
  fun provideFeatureDatabase(
    @FeatureDb dataSource: javax.inject.Provider<DataSource>
  ): FeatureDatabase {
    val driver = object : JdbcDriver() {
      override fun getConnection(): Connection {
        val connection = dataSource.get().connection
        connection.autoCommit = true
        return connection
      }

      override fun notifyListeners(queryKeys: Array<String>) {
      }

      override fun removeListener(listener: Query.Listener, queryKeys: Array<String>) {
      }

      override fun addListener(listener: Query.Listener, queryKeys: Array<String>) {
      }

      override fun closeConnection(connection: Connection) {
        connection.close()
      }
    }
    return FeatureDatabase(
      driver = driver,
      featuresAdapter = featuresAdapter,
    )
  }

  @Provides
  @Singleton
  fun provideFeatureQueries(
    database: FeatureDatabase
  ): FeatureQueries = database.featureQueries
}
