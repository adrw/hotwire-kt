package misk.db.feature.web.details

import kotlinx.html.TagConsumer
import kotlinx.html.a
import kotlinx.html.br
import kotlinx.html.code
import kotlinx.html.div
import kotlinx.html.h5
import kotlinx.html.id
import kotlinx.html.span
import kotlinx.html.style
import misk.db.feature.getFeatureClazz
import misk.db.feature.toFeatureType
import misk.db.feature.web.PathBuilder
import misk.db.feature.web.create.CreatePath
import misk.db.feature.web.results.ResultsPath
import misk.db.protos.feature.Feature
import xyz.adrw.hotwire.templates.TurboStreamAction
import xyz.adrw.hotwire.templates.template
import xyz.adrw.hotwire.templates.turbo_frame
import xyz.adrw.hotwire.templates.turbo_stream

const val DetailsPath = "flag"

data class FlagDetailsHtmlProps(
  val formId: String,
  val resultsId: String,
  val query: String,
  val feature: Feature?,
)

fun TagConsumer<*>.FlagDetailsHtml(props: FlagDetailsHtmlProps) {
  div {
    FlagResultsFrame(props)
  }
}

fun TagConsumer<*>.FlagResultsFrame(props: FlagDetailsHtmlProps) {
  turbo_frame(props.resultsId) {
    FlagResults(props)
  }
}

fun TagConsumer<*>.FlagDetailsStream(props: FlagDetailsHtmlProps) {
  turbo_stream(TurboStreamAction.REPLACE, props.resultsId) {
    template {
      div {
        id = props.resultsId
        FlagResults(props)
      }
    }
  }
}

fun TagConsumer<*>.FlagResults(props: FlagDetailsHtmlProps) {
  div("mt-1 relative rounded-md shadow-sm") {
    a(classes = "block text-right") {
      href = PathBuilder(path = ResultsPath).build()
      attributes["data-turbo"] = "false"
      +"""All Flags"""
    }
    if (props.feature == null) {
      h5("text-4xl font-bold text-gray-900") {
        +"Feature '${props.query}' does not exist"
      }
    } else {
      code("text-4xl font-bold text-gray-900") {
        +props.feature.name
      }
      span { style = "display: inline-block; width: 50px;" }
      span("text-4xl text-gray-900") {
        +"""Flag Type: ${props.feature.config!!.getFeatureClazz().toFeatureType().name.lowercase()}"""
      }
      span { style = "display: inline-block; width: 50px;" }
      a {
        href = PathBuilder(
          path = CreatePath,
          is_update = true,
          query = props.feature.name,
          feature_type_index = props.feature.config!!.getFeatureClazz().toFeatureType().ordinal
        ).build()
        attributes["data-turbo"] = "false"
        +"""Edit"""
      }
      br {}
      br {}
      h5("font-bold text-gray-900") { +"""Rules""" }
      props.feature.config?.rules?.forEachIndexed { index, rule ->
        code {
          +"${index + 1} - $rule\n"
        }
      }
    }

  }
}
