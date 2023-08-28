package misk.db.feature.web.v1.actions.pages

import kotlinx.html.script
import misk.db.feature.web.v1.results.ResultsHandler
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.buildHtml
import xyz.adrw.hotwire.templates.turbo_frame
import javax.inject.Inject

class V1IndexHandler @Inject private constructor(
  private val resultsHandler: ResultsHandler,
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
          resultsHandler.get(this.consumer, ResultsHandler.Props(query))
        }
      }
    }
  }

  companion object {
    private const val TAB_ROOT_ID = "tab-root"
  }
}
