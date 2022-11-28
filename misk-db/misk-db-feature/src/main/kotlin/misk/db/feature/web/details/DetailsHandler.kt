package misk.db.feature.web.details

import misk.db.feature.web.InternalApi
import misk.db.feature.web.results.ResultsFormId
import misk.db.feature.web.results.ResultsId
import misk.db.protos.feature.Feature
import misk.db.protos.feature.GetFeaturesRequest
import xyz.adrw.hotwire.templates.component
import javax.inject.Inject

class DetailsHandler @Inject private constructor(
  private val api: InternalApi
) {
  fun get() = component<Props> { props ->
    val allFlags = api.GetFeatures(GetFeaturesRequest(props.query))

    FlagDetailsHtml(
      FlagDetailsHtmlProps(
        formId = ResultsFormId,
        resultsId = ResultsId,
        query = props.query ?: "",
        feature = allFlags.features.firstOrNull()
      )
    )
  }

  fun stream() = component<Props> { props ->
    val allFlags = api.GetFeatures(GetFeaturesRequest())

    FlagDetailsStream(
      FlagDetailsHtmlProps(
        formId = ResultsFormId,
        resultsId = ResultsId,
        query = props.query ?: "",
        feature = allFlags.features.firstOrNull()
      )
    )
  }

  data class Props(
    val query: String? = null
  )
}
