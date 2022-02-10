package xyz.adrw.hotwire.html.components.tailwinds

import xyz.adrw.hotwire.html.hotwire.turbo_frame
import xyz.adrw.hotwire.html.infra.template
import kotlinx.html.div
import kotlinx.html.span
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.tr

data class TableProps(
  val data: List<List<String>>,
  val limit: Int? = null,
  val query: String? = null,
)

val TableId = "table_frame"

val Table = template<TableProps> { props ->
  val header = props.data.first()
  val dataRows = props.query?.let { query ->
    props.data.drop(1).filter { row ->
      row.any { it.contains(query) }
    }
  } ?: props.data.drop(1)
  val truncated = dataRows.take(props.limit ?: 5)

  turbo_frame(TableId) {
    div("flex flex-col") {
      div("-my-2 overflow-x-auto sm:-mx-6 lg:-mx-8") {
        div("py-2 align-middle inline-block min-w-full sm:px-6 lg:px-8") {
          div("shadow overflow-hidden border-b border-gray-200 sm:rounded-lg") {
            table("min-w-full divide-y divide-gray-200") {
              thead("bg-gray-50") {
                tr {
                  header.map {
                    th(classes = "px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider") {
                      attributes["scope"] = "col"
                      +it
                    }
                  }
                  th(classes = "relative px-6 py-3") {
                    attributes["scope"] = "col"
                    span("sr-only") { +"""Edit""" }
                  }
                }
              }
              tbody("bg-white divide-y divide-gray-200") {
                truncated.map { row ->
                  tr {
                    row.map { cell ->
                      td("px-6 py-4 whitespace-nowrap text-sm text-gray-500") { +cell }
                    }
//                  td("px-6 py-4 whitespace-nowrap") {
//                    div("flex items-center") {
//                      div("flex-shrink-0 h-10 w-10") {
//                        img(classes = "h-10 w-10 rounded-full") {
//                          src =
//                            "https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=4&w=256&h=256&q=60"
//                          alt = ""
//                        }
//                      }
//                      div("ml-4") {
//                        div("text-sm font-medium text-gray-900") { +"""Jane Cooper""" }
//                        div("text-sm text-gray-500") { +"""jane.cooper@example.com""" }
//                      }
//                    }
//                  }
//                  td("px-6 py-4 whitespace-nowrap") {
//                    div("text-sm text-gray-900") { +"""Regional Paradigm Technician""" }
//                    div("text-sm text-gray-500") { +"""Optimization""" }
//                  }
//                  td("px-6 py-4 whitespace-nowrap") {
//                    span("px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800") { +"""Active""" }
//                  }
//                  td("px-6 py-4 whitespace-nowrap text-sm text-gray-500") { +"""Admin""" }
//                  td("px-6 py-4 whitespace-nowrap text-right text-sm font-medium") {
//                    a(classes = "text-indigo-600 hover:text-indigo-900") {
//                      href = "#"
//                      +"""Edit"""
//                    }
//                  }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
