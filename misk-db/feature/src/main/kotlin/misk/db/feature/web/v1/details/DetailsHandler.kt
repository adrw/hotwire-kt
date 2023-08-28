package misk.db.feature.web.v1.details

import kotlinx.html.TagConsumer
import misk.db.feature.web.InternalApi
import misk.db.feature.web.v1.results.ResultsFormId
import misk.db.feature.web.v1.results.ResultsId
import misk.db.protos.feature.GetFeaturesRequest
import javax.inject.Inject

class DetailsHandler @Inject private constructor(
  private val api: InternalApi
) {
  fun get(tagConsumer: TagConsumer<*>, props: Props) {
    val allFlags = api.GetFeatures(GetFeaturesRequest(props.query))

    tagConsumer.FlagDetailsHtml(
      FlagDetailsHtmlProps(
        formId = ResultsFormId,
        resultsId = ResultsId,
        query = props.query ?: "",
        feature = allFlags.features.firstOrNull()
      )
    )
  }

  fun stream(tagConsumer: TagConsumer<*>, props: Props) {
    val allFlags = api.GetFeatures(GetFeaturesRequest())

    tagConsumer.FlagDetailsStream(
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
