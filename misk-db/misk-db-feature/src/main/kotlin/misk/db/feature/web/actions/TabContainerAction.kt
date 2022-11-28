package misk.db.feature.web.actions

import misk.web.Get
import misk.web.QueryParam
import misk.web.RequestContentType
import misk.web.ResponseContentType
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import misk.web.mediatype.MediaTypes
import javax.inject.Inject

/**
 * At tab launch, a TurboStream response is sent back to fill in the UI in the Misk admin dashboard
 *  with server generated Hotwire code instead of pure frontend React code.
 *
 */
class TabContainerAction @Inject constructor(
  private val handler: TabContainerHandler
): WebAction {
  @Get("/misk.db.feature.web/TabContainer")
  @ResponseContentType(MediaTypes.TEXT_HTML)
  @AdminDashboardAccess // set to dashboard access since this is UI layer
  fun get(
    @QueryParam frame: String?,
    @QueryParam path: String?,
    @QueryParam boolean: String?,
    @QueryParam q: String?,
    @QueryParam limit: String?,
    @QueryParam is_update: String?,
    @QueryParam feature_type_index: String?,
    // Additional query parameter encoded data is explicitly added here
  ) = handler.handle(frame, path, boolean, q, limit, is_update, feature_type_index)
}
