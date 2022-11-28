package misk.db.feature

import misk.db.FeatureQueries
import misk.db.feature.DbFeatureFlags.Companion.evaluate
import misk.db.feature.web.InternalApi
import misk.db.protos.feature.CreateOrUpdateFeatureRequest
import misk.db.protos.feature.DeleteFeatureRequest
import misk.db.protos.feature.FeatureConfig
import misk.db.protos.feature.FeatureRule
import misk.db.protos.feature.GetFeaturesRequest
import misk.feature.Attributes
import misk.feature.DynamicConfig
import misk.feature.Feature
import misk.feature.FeatureFlags
import misk.testing.MiskTest
import misk.testing.MiskTestModule
import org.junit.jupiter.api.Test
import xyz.adrw.flagpole.api.FlagpoleInternalApi
import xyz.adrw.flagpole.api.GetBillboardsAction.Companion.ENABLE_SEARCH_FEATURE
import xyz.adrw.protos.flagpole.CreateBillboardRequest
import xyz.adrw.protos.flagpole.GetBillboardsRequest
import javax.inject.Inject
import kotlin.test.assertEquals
import kotlin.test.assertNull

@MiskTest(startService = true)
class DbFeatureFlagsTest {
  @MiskTestModule
  private val module = DbFeatureFlagsTestModule()

  @Inject lateinit var featureQueries: FeatureQueries
  @Inject internal lateinit var internalApi: InternalApi
  @Inject lateinit var flagpoleInternalApi: FlagpoleInternalApi
  @Inject lateinit var dynamicConfig: DynamicConfig
  @Inject lateinit var featureFlags: FeatureFlags

  private val ENABLE_ALPHA_FEATURE = Feature("enable-alpha")

  @Test
  internal fun `full feature lifecycle with apis`() {
    // Confirm database is empty
    assertEquals(0, featureQueries.count().executeAsOne())

    // Create a flag
    internalApi.CreateOrUpdateFeature(
      CreateOrUpdateFeatureRequest(
        name = "enable-alpha",
        config = FeatureConfig(
          listOf(
            FeatureRule(value_boolean = true)
          )
        )
      )
    )
    val dbCreated = featureQueries.get("enable-alpha").executeAsOne()
    assertEquals("enable-alpha", dbCreated.name)
    assertEquals(
      FeatureConfig(
        listOf(
          FeatureRule(value_boolean = true)
        )
      ), dbCreated.metadata.config
    )

    // Get multiple flags
    internalApi.CreateOrUpdateFeature(
      CreateOrUpdateFeatureRequest(
        name = "bravo-mode",
        config = FeatureConfig(
          listOf(
            FeatureRule(value_string = "SINGLE")
          )
        )
      )
    )
    val apiGetAll = internalApi.GetFeatures(GetFeaturesRequest())
    assertEquals(2, apiGetAll.features.size)
    assertEquals("bravo-mode", apiGetAll.features.map { it.name }.first())
    assertEquals("enable-alpha", apiGetAll.features.map { it.name }.last())

    // Delete a flag
    val deleted = internalApi.DeleteFeature(DeleteFeatureRequest(name = "bravo-mode"))
    assertEquals("bravo-mode", deleted.deleted)
    assertNull(featureQueries.get("bravo-mode").executeAsOneOrNull())

    // Get value of injected flag
    val actualDynamic = dynamicConfig.getBoolean(ENABLE_ALPHA_FEATURE)
    assertEquals(true, actualDynamic)
    val actualFlags = featureFlags.getBoolean(ENABLE_ALPHA_FEATURE, "")
    assertEquals(true, actualFlags)
  }

  @Test
  internal fun `flagpole service enable search by flag`() {
    flagpoleInternalApi.CreateBillboard(
      CreateBillboardRequest(
        client_name = "Apple LLC",
        lcoation = "SF1",
      )
    )
    flagpoleInternalApi.CreateBillboard(
      CreateBillboardRequest(
        client_name = "Apple LLC",
        lcoation = "SF2",
      )
    )
    flagpoleInternalApi.CreateBillboard(
      CreateBillboardRequest(
        client_name = "Google LLC",
        lcoation = "SF1",
      )
    )
    flagpoleInternalApi.CreateBillboard(
      CreateBillboardRequest(
        client_name = "Google LLC",
        lcoation = "SF3",
      )
    )

    // Flag is off, all billboards returned
    internalApi.CreateOrUpdateFeature(
      CreateOrUpdateFeatureRequest(
        name = ENABLE_SEARCH_FEATURE.name,
        config = FeatureConfig(
          rules = listOf(
            FeatureRule(value_boolean = false)
          )
        )
      )
    )
    assertEquals(false, dynamicConfig.getBoolean(ENABLE_SEARCH_FEATURE))
    val responseFlagOff = flagpoleInternalApi.GetBillboards(GetBillboardsRequest("Apple"))
    assertEquals(4, responseFlagOff.billboards.size)

    // Flag is on, only Apple billboards returned
    internalApi.CreateOrUpdateFeature(
      CreateOrUpdateFeatureRequest(
        name = ENABLE_SEARCH_FEATURE.name,
        config = FeatureConfig(
          rules = listOf(
            FeatureRule(value_boolean = true)
          )
        )
      )
    )
    assertEquals(true, dynamicConfig.getBoolean(ENABLE_SEARCH_FEATURE))
    val responseFlagOn = flagpoleInternalApi.GetBillboards(GetBillboardsRequest("Apple"))
    assertEquals(2, responseFlagOn.billboards.size)
    assert(responseFlagOn.billboards.all { it.client_name == "Apple LLC" })

    // Flag is off, all billboards returned
    internalApi.CreateOrUpdateFeature(
      CreateOrUpdateFeatureRequest(
        name = ENABLE_SEARCH_FEATURE.name,
        config = FeatureConfig(
          rules = listOf(
            FeatureRule(value_boolean = false)
          )
        )
      )
    )
    assertEquals(false, dynamicConfig.getBoolean(ENABLE_SEARCH_FEATURE))
    val responseFlagOff2 = flagpoleInternalApi.GetBillboards(GetBillboardsRequest("Apple"))
    assertEquals(4, responseFlagOff2.billboards.size)
  }

  /** Confirm flags can exist in all types (int, double, string, enum, json, boolean) */
  @Test
  internal fun `all flag types`() {
    val defaultKey = "fake_dynamic_flag"
    val defaultAttributes = Attributes()

    val booleanFeatureConfig = FeatureConfig(
      rules = listOf(FeatureRule(value_boolean = true))
    )
    assertEquals(true, booleanFeatureConfig.evaluate(defaultKey, defaultAttributes))
    val doubleFeatureConfig = FeatureConfig(
      rules = listOf(FeatureRule(value_double = 3.16))
    )
    assertEquals(3.16, doubleFeatureConfig.evaluate(defaultKey, defaultAttributes))
    val intFeatureConfig = FeatureConfig(
      rules = listOf(FeatureRule(value_int = 316))
    )
    assertEquals(316, intFeatureConfig.evaluate(defaultKey, defaultAttributes))
    val stringFeatureConfig = FeatureConfig(
      rules = listOf(FeatureRule(value_string = "dynamic copy"))
    )
    assertEquals("dynamic copy", stringFeatureConfig.evaluate(defaultKey, defaultAttributes))
    val enumFeatureConfig = FeatureConfig(
      rules = listOf(
        FeatureRule(
          value_enum = "BRAVO",
          type_java_class_name = TestEnum::class.java.name
        )
      )
    )
    assertEquals(TestEnum.BRAVO, enumFeatureConfig.evaluate(defaultKey, defaultAttributes))
    val jsonFeatureConfig = FeatureConfig(
      rules = listOf(
        FeatureRule(
          value_json = """{"alpha":"bingo","bravo":"bongo"}""",
          type_java_class_name = TestJson::class.java.name
        )
      )
    )
    assertEquals(
      TestJson("bingo", "bongo"),
      jsonFeatureConfig.evaluate(defaultKey, defaultAttributes)
    )
  }

  @Test
  internal fun `parse type from config`() {
    val booleanFeatureConfig = FeatureConfig(
      rules = listOf(FeatureRule(value_boolean = true))
    )
    assertEquals(java.lang.Boolean::class.java, booleanFeatureConfig.getFeatureClazz())
    val doubleFeatureConfig = FeatureConfig(
      rules = listOf(FeatureRule(value_double = 3.16))
    )
    assertEquals(java.lang.Double::class.java, doubleFeatureConfig.getFeatureClazz())
    val intFeatureConfig = FeatureConfig(
      rules = listOf(FeatureRule(value_int = 316))
    )
    assertEquals(java.lang.Integer::class.java, intFeatureConfig.getFeatureClazz())
    val stringFeatureConfig = FeatureConfig(
      rules = listOf(FeatureRule(value_string = "dynamic copy"))
    )
    assertEquals(java.lang.String::class.java, stringFeatureConfig.getFeatureClazz())
    val enumFeatureConfig = FeatureConfig(
      rules = listOf(
        FeatureRule(
          value_enum = "BRAVO",
          type_java_class_name = TestEnum::class.java.name
        )
      )
    )
    assertEquals(TestEnum::class.java, enumFeatureConfig.getFeatureClazz())
    val jsonFeatureConfig = FeatureConfig(
      rules = listOf(
        FeatureRule(
          value_json = """{"alpha":"bingo","bravo":"bongo"}""",
          type_java_class_name = TestJson::class.java.name
        )
      )
    )
    assertEquals(TestJson::class.java, jsonFeatureConfig.getFeatureClazz())
  }

  @Test
  internal fun `evaluate rules`() {
    // Evaluate the list of rules in flag config

    // TODO evaluate multiple flags with keyed and attributes targets
  }

  enum class TestEnum {
    ALPHA,
    BRAVO
  }

  data class TestJson(
    val alpha: String,
    val bravo: String,
  )
}
