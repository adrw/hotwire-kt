package misk.db.flagpole.api

import com.squareup.moshi.Moshi
import misk.db.FeatureQueries
import misk.db.protos.feature.FeatureConfig
import misk.db.protos.feature.FeatureMetadata
import misk.db.protos.feature.FeatureRule
import misk.web.Get
import misk.web.RequestBody
import misk.web.RequestContentType
import misk.web.Response
import misk.web.ResponseBody
import misk.web.ResponseContentType
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import misk.web.mediatype.MediaTypes
import misk.web.toResponseBody
import misk.db.flagpole.api.DebugGetFeaturesAction.Companion.TestEnum.RED
import java.time.Clock
import javax.inject.Inject

class DebugSeedTestFeaturesAction @Inject constructor(
  private val clock: Clock,
  private val features: FeatureQueries,
  private val moshi: Moshi,
): WebAction {
  @Get("/debug/seed")
  @ResponseContentType(MediaTypes.APPLICATION_JSON)
  @AdminDashboardAccess
  fun seed(): Response<ResponseBody> {
    features.insert(
      created_at = clock.instant(),
      updated_at = clock.instant(),
      name = "test-bool",
      metadata = FeatureMetadata(
        config = FeatureConfig(
          rules = listOf(
            FeatureRule(
              value_boolean = true,
            )
          )
        )
      )
    )
    features.insert(
      created_at = clock.instant(),
      updated_at = clock.instant(),
      name = "test-double",
      metadata = FeatureMetadata(
        config = FeatureConfig(
          rules = listOf(
            FeatureRule(
              value_double = 24.48,
            )
          )
        )
      )
    )
    features.insert(
      created_at = clock.instant(),
      updated_at = clock.instant(),
      name = "test-int",
      metadata = FeatureMetadata(
        config = FeatureConfig(
          rules = listOf(
            FeatureRule(
              value_int = 24,
            )
          )
        )
      )
    )
    features.insert(
      created_at = clock.instant(),
      updated_at = clock.instant(),
      name = "test-string",
      metadata = FeatureMetadata(
        config = FeatureConfig(
          rules = listOf(
            FeatureRule(
              value_string = "test value",
            )
          )
        )
      )
    )
    features.insert(
      created_at = clock.instant(),
      updated_at = clock.instant(),
      name = "test-enum",
      metadata = FeatureMetadata(
        config = FeatureConfig(
          rules = listOf(
            FeatureRule(
              value_enum = RED.name,
              type_java_class_name = DebugGetFeaturesAction.Companion.TestEnum::class.java.name
            )
          )
        )
      )
    )
    features.insert(
      created_at = clock.instant(),
      updated_at = clock.instant(),
      name = "test-json",
      metadata = FeatureMetadata(
        config = FeatureConfig(
          rules = listOf(
            FeatureRule(
              value_json = moshi.adapter(DebugGetFeaturesAction.Companion.TestJson::class.java).toJson(
                DebugGetFeaturesAction.Companion.TestJson("test value", 24, true)
              ),
              type_java_class_name = DebugGetFeaturesAction.Companion.TestJson::class.java.name
            )
          )
        )
      )
    )
    return Response("seeded".toResponseBody())
  }
}
