package misk.db.flagpole.api

import misk.feature.DynamicConfig
import misk.feature.Feature
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import misk.db.protos.flagpole.DebugGetFeaturesRequest
import misk.db.protos.flagpole.DebugGetFeaturesResponse
import misk.db.protos.flagpole.FlagpoleApiServiceDebugGetFeaturesBlockingServer
import javax.inject.Inject

class DebugGetFeaturesAction @Inject constructor(
  private val dynamicConfig: DynamicConfig,
): FlagpoleApiServiceDebugGetFeaturesBlockingServer, WebAction {
  @AdminDashboardAccess
  override fun DebugGetFeatures(request: DebugGetFeaturesRequest): DebugGetFeaturesResponse =
    DebugGetFeaturesResponse(
      flag_bool = dynamicConfig.getBoolean(BOOL_FEATURE),
      flag_double = dynamicConfig.getDouble(DOUBLE_FEATURE),
      flag_int = dynamicConfig.getInt(INT_FEATURE),
      flag_string = dynamicConfig.getString(STRING_FEATURE),
      flag_enum = dynamicConfig.getEnum(ENUM_FEATURE, TestEnum::class.java).name,
      flag_json = dynamicConfig.getJson(JSON_FEATURE, TestJson::class.java).toString(),
    )

  companion object {
    val BOOL_FEATURE = Feature("test-bool")
    val DOUBLE_FEATURE = Feature("test-double")
    val INT_FEATURE = Feature("test-int")
    val STRING_FEATURE = Feature("test-string")
    val ENUM_FEATURE = Feature("test-enum")
    val JSON_FEATURE = Feature("test-json")

    enum class TestEnum {
      RED, GREEN, BLUE
    }

    data class TestJson(
      val alpha: String,
      val bravo: Int,
      val charlie: Boolean,
    )
  }
}
