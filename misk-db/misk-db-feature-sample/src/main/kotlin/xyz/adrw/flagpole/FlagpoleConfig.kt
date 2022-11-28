package xyz.adrw.flagpole

import misk.config.Config
import misk.jdbc.DataSourceClustersConfig
import misk.web.WebConfig

data class FlagpoleConfig(
  val web: WebConfig,
  val data_source_clusters: DataSourceClustersConfig,
  ) : Config
