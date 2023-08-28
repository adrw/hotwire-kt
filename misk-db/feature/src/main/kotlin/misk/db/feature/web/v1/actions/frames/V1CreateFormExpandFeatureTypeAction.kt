package misk.db.feature.web.v1.actions.frames

import misk.db.feature.web.PathBuilder.Companion.UI_FRAME_BASE_URL
import misk.db.feature.web.v1.create.CreateFormExpandFeatureTypeId
import misk.db.feature.web.v1.create.CreateOrUpdateHandler
import misk.web.Get
import misk.web.QueryParam
import misk.web.ResponseContentType
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import misk.web.mediatype.MediaTypes
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.buildHtml
import javax.inject.Inject

/**
 * Returns interactive UI from Hotwire Turbo Frame related clicks
 * Configuration of which UI to return and input data (ie. from forms) is provided by query parameters
 */
class V1CreateFormExpandFeatureTypeAction @Inject constructor(
  private val createOrUpdateHandler: CreateOrUpdateHandler
) : WebAction {
  @Get("$UI_FRAME_BASE_URL/$PATH/")
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
  ): String = buildHtml {
    Wrapper {
      createOrUpdateHandler.get(this,
        CreateOrUpdateHandler.Props(
          is_update = is_update?.toBooleanStrictOrNull() ?: false,
          select_input_id = select_input_id,
          select_input_is_expanded = select_input_is_expanded?.toBooleanStrictOrNull() ?: false,
          feature_type_index = feature_type_index?.toIntOrNull(),
          create_name = create_name ?: q,
          create_value = create_value,
          type_java_class_name = type_java_class_name,
        )
      )
    }
  }

  companion object {
    const val PATH = CreateFormExpandFeatureTypeId
  }
}


