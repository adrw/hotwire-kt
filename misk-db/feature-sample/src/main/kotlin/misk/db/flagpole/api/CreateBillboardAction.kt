package misk.db.flagpole.api

import misk.tokens.TokenGenerator
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import misk.db.flagpole.db.FlagpoleDatabase
import misk.db.protos.flagpole.CreateBillboardRequest
import misk.db.protos.flagpole.CreateBillboardResponse
import misk.db.protos.flagpole.FlagpoleApiServiceCreateBillboardBlockingServer
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
      location = request.location,
    )
    return CreateBillboardResponse(token)
  }
}
