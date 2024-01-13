package misk.db.flagpole.persistence

import app.cash.sqldelight.Query
import app.cash.sqldelight.driver.jdbc.JdbcDriver
import com.google.inject.Provides
import misk.db.feature.FeatureDb
import misk.inject.KAbstractModule
import misk.jdbc.JdbcModule
import misk.db.flagpole.FlagpoleConfig
import misk.db.flagpole.db.FlagpoleDatabase
import java.sql.Connection
import javax.inject.Provider
import javax.inject.Singleton
import javax.sql.DataSource

class FlagpolePersistenceModule(
  private val config: FlagpoleConfig
) : KAbstractModule() {
  override fun configure() {
    install(
      JdbcModule(
        FeatureDb::class,
        config.data_source_clusters.values.single().writer
      )
    )
    install(
      JdbcModule(
        FlagpoleDb::class,
        config.data_source_clusters.values.single().writer
      )
    )
  }

  @Provides
  @Singleton
  fun provideFlagpoleDatabase(
    @FlagpoleDb dataSource: Provider<DataSource>
  ): FlagpoleDatabase {
    val driver = object : JdbcDriver() {
      override fun getConnection(): Connection {
        val connection = dataSource.get().connection
        connection.autoCommit = true
        return connection
      }

      override fun notifyListeners(vararg queryKeys: String) {
      }

      override fun removeListener(vararg queryKeys: String, listener: Query.Listener) {
      }

      override fun addListener(vararg queryKeys: String, listener: Query.Listener) {
      }

      override fun closeConnection(connection: Connection) {
        connection.close()
      }
    }
    return FlagpoleDatabase(
      driver = driver,
      billboardsAdapter = billboardsAdapter,
    )
  }
}
