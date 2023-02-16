package misk.db.feature.web.actions.frames

import misk.web.Get
import misk.web.QueryParam
import misk.web.ResponseContentType
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import misk.web.mediatype.MediaTypes
import javax.inject.Inject

/**
 * Returns interactive UI from Hotwire Turbo Frame related clicks
 * Configuration of which UI to return and input data (ie. from forms) is provided by query parameters
 */
class TurboRenderAction @Inject constructor(
  private val handler: TurboRenderHandler
) : WebAction {
  @Get("/misk.db.feature.web/$PATH/")
  @ResponseContentType(MediaTypes.TEXT_HTML)
  @AdminDashboardAccess
  fun get(
    @QueryParam frame: String?,
    @QueryParam boolean: String?,
    @QueryParam q: String?,
    @QueryParam limit: String?,
    @QueryParam is_update: String?,
    @QueryParam select_input_id: String?,
    @QueryParam select_input_is_expanded: String?,
    @QueryParam feature_type_index: String?,
    @QueryParam create_name: String?,
    @QueryParam create_value: String?,
    @QueryParam type_java_class_name: String?,
    // Additional query parameter encoded data is explicitly added here
  ): String = handler.handle(
    frame = frame,
    boolean = boolean,
    query = q,
    limit = limit,
    is_update = is_update,
    select_input_id = select_input_id,
    select_input_is_expanded = select_input_is_expanded,
    feature_type_index = feature_type_index,
    create_name = create_name,
    create_value = create_value,
    type_java_class_name = type_java_class_name,
  )

  companion object {
    const val PATH = "TurboStream"
  }
}


