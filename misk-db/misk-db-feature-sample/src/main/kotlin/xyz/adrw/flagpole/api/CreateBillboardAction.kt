package xyz.adrw.flagpole.api

import misk.tokens.TokenGenerator
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import xyz.adrw.flagpole.db.FlagpoleDatabase
import xyz.adrw.protos.flagpole.CreateBillboardRequest
import xyz.adrw.protos.flagpole.CreateBillboardResponse
import xyz.adrw.protos.flagpole.FlagpoleApiServiceCreateBillboardBlockingServer
import java.time.Clock
import javax.inject.Inject

class CreateBillboardAction @Inject constructor(
  private val clock: Clock,
  private val database: FlagpoleDatabase,
  private val tokenGenerator: TokenGenerator,
): FlagpoleApiServiceCreateBillboardBlockingServer, WebAction {
  @AdminDashboardAccess
  override fun CreateBillboard(request: CreateBillboardRequest): CreateBillboardResponse {
    val token = tokenGenerator.generate("billboard")
    database.billboardsQueries.insert(
      created_at = clock.instant(),
      updated_at = clock.instant(),
      token = token,
      client_name = request.client_name,
      location = request.lcoation,
    )
    return CreateBillboardResponse(token)
  }
}
