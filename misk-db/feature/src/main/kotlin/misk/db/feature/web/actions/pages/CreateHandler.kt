package misk.db.feature.web.actions.pages

import kotlinx.html.script
import misk.db.feature.web.create.CreateOrUpdateHandler
import wisp.logging.getLogger
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.buildHtml
import xyz.adrw.hotwire.templates.turbo_frame
import javax.inject.Inject

class CreateHandler @Inject private constructor(
  private val createOrUpdateHandler: CreateOrUpdateHandler,
) {
  fun handle(
    frame: String?,
    path: String?,
    boolean: String?,
    query: String?,
    limit: String?,
    is_update: String?,
    feature_type_index: String?
  ): String {
    return buildHtml {
      Wrapper(headBlock = {
        script {
          type = "module"
          src = "/static/js/misk_db_feature_search_form_controller.js"
        }
      }) {
        turbo_frame(TAB_ROOT_ID) {
          createOrUpdateHandler.get(this.consumer,
            CreateOrUpdateHandler.Props(
              create_name = query,
              is_update = is_update?.toBooleanStrictOrNull() ?: false,
              feature_type_index = feature_type_index?.toIntOrNull(),
            )
          )
        }
      }
    }
  }

  companion object {
    private val logger = getLogger<CreateHandler>()

    private const val TAB_ROOT_ID = "tab-root"
  }
}
