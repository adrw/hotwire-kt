package xyz.adrw.flagpole.persistence

import com.google.inject.Provides
import com.squareup.sqldelight.sqlite.driver.JdbcDriver
import misk.db.feature.FeatureDb
import misk.db.feature.featuresAdapter
import misk.inject.KAbstractModule
import misk.jdbc.JdbcModule
import xyz.adrw.flagpole.FlagpoleConfig
import xyz.adrw.flagpole.db.Features
import xyz.adrw.flagpole.db.FlagpoleDatabase
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

      override fun closeConnection(connection: Connection) {
        connection.close()
      }
    }
    return FlagpoleDatabase(
      driver = driver,
      billboardsAdapter = billboardsAdapter,
      featuresAdapter = coerceToServiceAdapter(),
    )
  }

  private fun coerceToServiceAdapter() = Features.Adapter(
    idAdapter = featuresAdapter.idAdapter,
    created_atAdapter = featuresAdapter.created_atAdapter,
    updated_atAdapter = featuresAdapter.updated_atAdapter,
    metadataAdapter = featuresAdapter.metadataAdapter,
  )
}
