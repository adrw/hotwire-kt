package misk.db.feature.web.results

import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.br
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import misk.db.feature.web.PathBuilder
import misk.db.feature.web.PathBuilder.Companion.SearchParam
import misk.db.feature.web.create.CreatePath
import xyz.adrw.hotwire.tailwinds.Table
import xyz.adrw.hotwire.tailwinds.TableProps
import xyz.adrw.hotwire.templates.TurboStreamAction
import xyz.adrw.hotwire.templates.component
import xyz.adrw.hotwire.templates.template
import xyz.adrw.hotwire.templates.turbo_frame
import xyz.adrw.hotwire.templates.turbo_stream

const val ResultsPath = "/"
const val ResultsFormId = "feature-search-form"
const val ResultsId = "feature-results"

data class FlagPageHtmlProps(
  val formId: String,
  val resultsId: String,
  val query: String,
  val totalFlagsCount: Long,
  val flagResults: TableProps,
)

val FlagPageHtml = component<FlagPageHtmlProps> { props ->
  div {
    FlagForm(props)
    FlagResultsFrame(props)
  }
}

val FlagForm = component<FlagPageHtmlProps> { props ->
  div("mt-1 relative rounded-md shadow-sm") {
    form {
      action = PathBuilder(frame = ResultsFormId).build(public = false)
      attributes["data-controller"] = "misk-db-feature-search-form"
      attributes["data-misk-db-feature-search-form-target"] = "form"
      // TODO see if this URL redirect is the right one, whether it can be softer?
      attributes["data-action"] = "input->misk-db-feature-search-form#search"

      label("block text-right font-medium text-gray-700") {
        attributes["htmlFor"] = ResultsFormId
        +"""Searching ${props.totalFlagsCount} Flags"""
      }

      div("flex") {
        a {
          href = PathBuilder(path = CreatePath).build()
          attributes["data-turbo"] = "false"
          input(classes = "bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center") {
            type = InputType.button
            value = "Create"
          }
        }
        input(classes = "focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7 pr-12 sm:text-md border-gray-300 rounded-md") {
          attributes["data-misk-db-feature-search-form-target"] = "query"
          type = InputType.text
          name = SearchParam
          id = SearchParam
          placeholder = "flag-name"
          value = props.query
        }
      }
    }
  }
}

val FlagResultsFrame = component<FlagPageHtmlProps> { props ->
  turbo_frame(props.resultsId) {
    FlagResults(props)
  }
}

val FlagResultsStream = component<FlagPageHtmlProps> { props ->
  turbo_stream(TurboStreamAction.REPLACE, props.resultsId) {
    template {
      div {
        id = props.resultsId
        FlagResults(props)
      }
    }
  }
}

val FlagResults = component<FlagPageHtmlProps> { props ->
  div("mt-1 relative rounded-md shadow-sm") {
    br {}
    Table(props.flagResults)
    br {}
    br {}
  }
}
