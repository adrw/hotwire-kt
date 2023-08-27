package xyz.adrw.hotwire.tailwinds

import kotlinx.html.TagConsumer
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.span
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.tr
import xyz.adrw.hotwire.templates.Link
import xyz.adrw.hotwire.templates.turbo_frame
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class TableHeader(
  val header: String,
  val sortable: Boolean = false,
)

interface TableRow {
  /** When called returns a list in order of the cells for the row. */
  fun cells(): List<*>
}

data class TableProps(
  val id: String,
  val headers: List<TableHeader>,
  val data: List<TableRow>,
  val limit: Int? = null,
)

fun TagConsumer<*>.Table(props: TableProps) {
  val header = props.headers
  val dataRows = props.data
  val truncated = dataRows.take(props.limit ?: 25)

  turbo_frame(props.id) {
    div("flex flex-col") {
      div("-my-2 overflow-x-auto sm:-mx-6 lg:-mx-8") {
        div("py-2 align-middle inline-block min-w-full sm:px-6 lg:px-8") {
          div("shadow overflow-hidden border-b border-gray-200 sm:rounded-lg") {
            table("min-w-full divide-y divide-gray-200") {
              thead("bg-gray-50") {
                tr {
                  header.map {
                    th(classes = "px-6 py-3 text-left font-medium text-gray-500 uppercase tracking-wider") {
                      attributes["scope"] = "col"
                      +it.header
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
                    row.cells().map { cell ->
                      when (cell) {
                        is Instant -> {
                          td("px-6 py-4 whitespace-nowrap text-gray-500") {
                            val zonedDateTime = cell.atZone(ZoneId.systemDefault())
                            val formattedDate = zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)
                            val formattedTime = zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
                            +"""$formattedDate $formattedTime"""
                          }
                        }
                        is Link -> {
                          td("px-6 py-4 whitespace-nowrap font-medium") {
                            a(classes = "text-indigo-600 hover:text-indigo-900") {
                              href = cell.href
                              if (cell.dataTurbo) {
                                attributes["data-turbo-preload"]=""
                              } else {
                                attributes["data-turbo"] = "false"
                              }
                              if (cell.isPageNavigation) {
                                attributes["target"]="_top"
                              } else if (cell.openInNewTab) {
                                attributes["target"]="_blank"
                              }
                              +cell.label
                            }
                          }
                        }
                        is String -> {
                          td("px-6 py-4 whitespace-nowrap text-gray-500") {
                            +cell
                          }
                        }
                        else -> {
                          td("px-6 py-4 whitespace-nowrap text-gray-500") {
                            +"""$cell"""
                          }
                        }
                      }
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
//                        div("font-medium text-gray-900") { +"""Jane Cooper""" }
//                        div("text-gray-500") { +"""jane.cooper@example.com""" }
//                      }
//                    }
//                  }
//                  td("px-6 py-4 whitespace-nowrap") {
//                    div("text-gray-900") { +"""Regional Paradigm Technician""" }
//                    div("text-gray-500") { +"""Optimization""" }
//                  }
//                  td("px-6 py-4 whitespace-nowrap") {
//                    span("px-2 inline-flex leading-5 font-semibold rounded-full bg-green-100 text-green-800") { +"""Active""" }
//                  }
//                  td("px-6 py-4 whitespace-nowrap text-gray-500") { +"""Admin""" }
//                  td("px-6 py-4 whitespace-nowrap text-right font-medium") {
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
