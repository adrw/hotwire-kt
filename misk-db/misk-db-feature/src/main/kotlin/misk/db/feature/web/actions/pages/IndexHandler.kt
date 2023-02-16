package misk.db.feature.web.actions.pages

import kotlinx.html.script
import misk.db.feature.web.create.CreateOrUpdateHandler
import misk.db.feature.web.details.DetailsHandler
import misk.db.feature.web.results.ResultsHandler
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.buildHtml
import xyz.adrw.hotwire.templates.turbo_frame
import javax.inject.Inject

class IndexHandler @Inject private constructor(
  private val createOrUpdateHandler: CreateOrUpdateHandler,
  private val detailsHandler: DetailsHandler,
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
          resultsHandler.get()(ResultsHandler.Props(query))
        }
      }
    }
  }

  companion object {
    private const val TAB_ROOT_ID = "tab-root"
  }
}
