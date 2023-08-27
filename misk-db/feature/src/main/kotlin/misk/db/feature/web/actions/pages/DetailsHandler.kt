package misk.db.feature.web.actions.pages

import kotlinx.html.script
import misk.db.feature.web.details.DetailsHandler
import wisp.logging.getLogger
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.buildHtml
import xyz.adrw.hotwire.templates.turbo_frame
import javax.inject.Inject

class DetailsHandler @Inject private constructor(
  private val detailsHandler: DetailsHandler,
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
      Wrapper(headBlock =  {
        script {
          type = "module"
          src = "/static/js/misk_db_feature_search_form_controller.js"
        }
      }) {
        turbo_frame(TAB_ROOT_ID) {
          detailsHandler.get(this.consumer, DetailsHandler.Props(query))
        }
      }
    }
  }

  companion object {
    private val logger = getLogger<DetailsHandler>()

    private const val TAB_ROOT_ID = "tab-root"
  }
}
