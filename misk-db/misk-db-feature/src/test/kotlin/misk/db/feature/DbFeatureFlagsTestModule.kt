package misk.db.feature

import misk.MiskTestingServiceModule
import misk.config.ConfigModule
import misk.config.MiskConfig
import misk.environment.DeploymentModule
import misk.inject.KAbstractModule
import misk.jdbc.JdbcTestingModule
import misk.logging.LogCollectorModule
import misk.web.MiskWebModule
import wisp.deployment.TESTING
import xyz.adrw.flagpole.FlagpoleAccessModule
import xyz.adrw.flagpole.FlagpoleConfig
import xyz.adrw.flagpole.FlagpoleLogging
import xyz.adrw.flagpole.api.FlagpoleWebActionsModule
import xyz.adrw.flagpole.persistence.FlagpoleDb
import xyz.adrw.flagpole.persistence.FlagpolePersistenceModule

class DbFeatureFlagsTestModule : KAbstractModule() {
  override fun configure() {
    val config: FlagpoleConfig = MiskConfig.load(
      appName = SERVICE_NAME,
      deployment = TESTING
    )
    install(DeploymentModule())
    FlagpoleLogging.configure()

    install(ConfigModule.create(SERVICE_NAME, config))
    install(LogCollectorModule())
    install(MiskWebModule(config.web))
    install(MiskTestingServiceModule())

    install(FlagpolePersistenceModule(config))
    install(FlagpoleAccessModule())
    install(FlagpoleWebActionsModule())
    install(DbFeatureFlagsModule(TESTING.isLocalDevelopment))
    install(JdbcTestingModule(FeatureDb::class))
    install(JdbcTestingModule(FlagpoleDb::class))
  }

  companion object {
    internal const val SERVICE_NAME = "flagpole"
  }
}
