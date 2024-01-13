package misk.db.flagpole

import misk.MiskApplication
import misk.MiskRealServiceModule
import misk.config.ConfigModule
import misk.config.MiskConfig
import misk.db.feature.DbFeatureFlagsModule
import misk.environment.DeploymentModule
import misk.web.dashboard.AdminDashboardModule
import wisp.deployment.DEVELOPMENT
import misk.db.flagpole.api.DebugGetFeaturesAction
import misk.db.flagpole.api.DebugGetFeaturesAction.Companion.BOOL_FEATURE
import misk.db.flagpole.api.DebugGetFeaturesAction.Companion.DOUBLE_FEATURE
import misk.db.flagpole.api.DebugGetFeaturesAction.Companion.ENUM_FEATURE
import misk.db.flagpole.api.DebugGetFeaturesAction.Companion.INT_FEATURE
import misk.db.flagpole.api.DebugGetFeaturesAction.Companion.JSON_FEATURE
import misk.db.flagpole.api.DebugGetFeaturesAction.Companion.STRING_FEATURE
import misk.db.flagpole.api.FlagpoleWebActionsModule
import misk.db.flagpole.persistence.FlagpolePersistenceModule

private const val SERVICE_NAME = "flagpole"

fun main(args: Array<String>) {
  val deployment = DEVELOPMENT
  FlagpoleLogging.configure()
  val config = MiskConfig.load<FlagpoleConfig>(SERVICE_NAME, deployment)
  MiskApplication(
    MiskRealServiceModule(),
    CommonWebServiceModule(config),
    FlagpoleAccessModule(),
    FlagpolePersistenceModule(config),
    FlagpoleWebActionsModule(),
    ConfigModule.create(SERVICE_NAME, config),
    DeploymentModule(deployment),
    AdminDashboardModule(deployment.isLocalDevelopment),
    DbFeatureFlagsModule(deployment.isLocalDevelopment).withDefaults {
      default(BOOL_FEATURE, true)
      default(DOUBLE_FEATURE, 24.48)
      default(INT_FEATURE, 42)
      default(STRING_FEATURE, "he that seeks, finds")
      default(ENUM_FEATURE, DebugGetFeaturesAction.Companion.TestEnum.RED)
      defaultJson(JSON_FEATURE, DebugGetFeaturesAction.Companion.TestJson(
        alpha = "bloop",
        bravo = 62,
        charlie = true,
      ))
    },
  ).run(args)
}
