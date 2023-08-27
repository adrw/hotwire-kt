package misk.db.feature

import misk.MiskTestingServiceModule
import misk.config.ConfigModule
import misk.config.MiskConfig
import misk.environment.DeploymentModule
import misk.inject.KAbstractModule
import misk.jdbc.JdbcTestingModule
import misk.logging.LogCollectorModule
import misk.web.MiskWebModule
import misk.web.dashboard.AdminDashboardTestingModule
import wisp.deployment.TESTING
import misk.db.flagpole.FlagpoleAccessModule
import misk.db.flagpole.FlagpoleConfig
import misk.db.flagpole.FlagpoleLogging
import misk.db.flagpole.api.FlagpoleWebActionsModule
import misk.db.flagpole.persistence.FlagpoleDb
import misk.db.flagpole.persistence.FlagpolePersistenceModule

class DbFeatureFlagsTestModule : KAbstractModule() {
  override fun configure() {
    val deployment = TESTING
    val config: FlagpoleConfig = MiskConfig.load(
      appName = SERVICE_NAME,
      deployment = deployment
    )
    install(DeploymentModule(deployment))
    FlagpoleLogging.configure()

    install(ConfigModule.create(SERVICE_NAME, config))
    install(LogCollectorModule())
    install(MiskWebModule(config.web))
    install(MiskTestingServiceModule())
    install(AdminDashboardTestingModule())

    install(JdbcTestingModule(FeatureDb::class))
    install(JdbcTestingModule(FlagpoleDb::class))
    install(FlagpolePersistenceModule(config))
    install(FlagpoleAccessModule())
    install(FlagpoleWebActionsModule())
    install(DbFeatureFlagsModule(deployment.isLocalDevelopment))
  }

  companion object {
    internal const val SERVICE_NAME = "flagpole"
  }
}
