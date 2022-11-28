package xyz.adrw.flagpole

import misk.MiskApplication
import misk.MiskRealServiceModule
import misk.config.ConfigModule
import misk.config.MiskConfig
import misk.db.feature.DbFeatureFlagsModule
import misk.environment.DeploymentModule
import misk.web.dashboard.AdminDashboardModule
import wisp.deployment.DEVELOPMENT
import xyz.adrw.flagpole.api.FlagpoleWebActionsModule
import xyz.adrw.flagpole.persistence.FlagpolePersistenceModule

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
    DbFeatureFlagsModule(deployment.isLocalDevelopment),
  ).run(args)
}
