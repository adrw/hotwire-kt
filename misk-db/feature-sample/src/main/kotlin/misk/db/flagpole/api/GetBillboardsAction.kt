package misk.db.flagpole.api

import misk.feature.DynamicConfig
import misk.feature.Feature
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import misk.db.flagpole.db.FlagpoleDatabase
import misk.db.protos.flagpole.FlagpoleApiServiceGetBillboardsBlockingServer
import misk.db.protos.flagpole.GetBillboardsRequest
import misk.db.protos.flagpole.GetBillboardsResponse
import javax.inject.Inject

class GetBillboardsAction @Inject constructor(
  private val database: FlagpoleDatabase,
  private val dynamicConfig: DynamicConfig,
): FlagpoleApiServiceGetBillboardsBlockingServer, WebAction {
  @AdminDashboardAccess
  override fun GetBillboards(request: GetBillboardsRequest): GetBillboardsResponse {
    val billboards = if (dynamicConfig.getBoolean(ENABLE_SEARCH_FEATURE)) {
      database.billboardsQueries.getAll().executeAsList()
        .filter { it.client_name.contains(request.search_query) ||
          it.location.contains(request.search_query) }
    } else {
      database.billboardsQueries.getAll().executeAsList()
    }.map { GetBillboardsResponse.Billboard(
      token = it.token,
      client_name = it.client_name,
      location = it.location,
    ) }
    return GetBillboardsResponse(billboards)
  }

  companion object {
    val ENABLE_SEARCH_FEATURE = Feature("enable-search")
  }
}
