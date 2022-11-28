package misk.db.feature.web.actions

import kotlinx.html.script
import misk.db.feature.web.PathBuilder
import misk.db.feature.web.create.CreateOrUpdateHandler
import misk.db.feature.web.create.CreatePath
import misk.db.feature.web.details.DetailsHandler
import misk.db.feature.web.details.DetailsPath
import misk.db.feature.web.results.ResultsHandler
import wisp.logging.getLogger
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.buildHtml
import xyz.adrw.hotwire.templates.turbo_frame
import javax.inject.Inject

class TabContainerHandler @Inject private constructor(
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
        when (path) {
          "${PathBuilder.TabPublicUrlBase}/" -> turbo_frame(TAB_ROOT_ID) {
            resultsHandler.get()(ResultsHandler.Props(query))
          }
          "${PathBuilder.TabPublicUrlBase}/$DetailsPath/" -> turbo_frame(TAB_ROOT_ID) {
            detailsHandler.get()(DetailsHandler.Props(query))
          }
          "${PathBuilder.TabPublicUrlBase}/$CreatePath/" -> turbo_frame(TAB_ROOT_ID) {
            createOrUpdateHandler.get()(CreateOrUpdateHandler.Props(
              create_name = query,
              is_update = is_update?.toBooleanStrictOrNull() ?: false,
              feature_type_index = feature_type_index?.toIntOrNull(),
            ))
          }
        }
      }
    }
  }

  companion object {
    private val logger = getLogger<TabContainerHandler>()

    private const val TAB_ROOT_ID = "tab-root"
  }
}
