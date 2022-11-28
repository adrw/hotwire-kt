package misk.db.feature.web.results

import misk.db.feature.web.InternalApi
import misk.db.feature.web.results.FlagSearchResultsTableRow.Companion.toTableRow
import misk.db.protos.feature.GetFeaturesRequest
import xyz.adrw.hotwire.tailwinds.TableProps
import xyz.adrw.hotwire.templates.component
import javax.inject.Inject

class ResultsHandler @Inject private constructor(
  private val api: InternalApi
) {
  fun get() = component<Props> { props ->
    val allFlags = api.GetFeatures(GetFeaturesRequest(props.query))

    FlagPageHtml(
      FlagPageHtmlProps(
        formId = ResultsFormId,
        resultsId = ResultsId,
        query = props.query ?: "",
        totalFlagsCount = allFlags.total_features_count,
        flagResults = TableProps(
          id = ResultsId,
          headers = flagHeaders,
          data = allFlags.features.map { it.toTableRow() },
          limit = 50
        ),
      )
    )
  }

  fun stream() = component<Props> { props ->
    val allFlags = api.GetFeatures(GetFeaturesRequest(props.query))

    FlagResultsStream(
      FlagPageHtmlProps(
        formId = ResultsFormId,
        resultsId = ResultsId,
        query = props.query ?: "",
        totalFlagsCount = allFlags.total_features_count,
        flagResults = TableProps(
          id = ResultsId,
          headers = flagHeaders,
          data = allFlags.features.map { it.toTableRow() },
          limit = 50
        ),
      )
    )
  }

  data class Props(
    val query: String? = null
  )
}
