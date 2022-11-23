package xyz.adrw.hotwire.tailwinds

import kotlinx.html.InputType
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import xyz.adrw.hotwire.templates.component
import xyz.adrw.hotwire.templates.turbo_frame

data class TableWithQueryProps(
  val data: List<List<String>> = loadingData,
  val limit: Int? = null,
  val query: String? = null,
  val formPath: String = "#",
  val searchParam: String,
  val limitParam: String,
) {
  fun toStringTableProps() = StringTableProps(
    data = data,
    limit = limit,
    query = query
  )

  companion object {
    val loadingData = listOf(
      listOf(
        "Column A", "Column B", "Column C"
      ),
      listOf(
        "---", "---", "---"
      ),
      listOf(
        "---", "---", "---"
      ),
    )
  }
}

val TableWithQueryId = "table_query_frame"

val TableWithQuery = component<TableWithQueryProps> { props ->
  turbo_frame(TableWithQueryId) {
    div {
      div("mt-1 relative rounded-md shadow-sm") {
//              div("absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none") {
//                span("text-gray-500 sm:text-sm") { +"""Title""" }
//              }
        form {
          action = props.formPath
          label("block text-sm font-medium text-gray-700") {
            attributes["htmlFor"] = props.searchParam
            +"""Search"""
          }
          input(classes = "focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7 pr-12 sm:text-sm border-gray-300 rounded-md") {
            type = InputType.text
            // appears in path param
            name = props.searchParam
            id = props.searchParam
            placeholder = "Buick"
          }
          label("block text-sm font-medium text-gray-700") {
            attributes["htmlFor"] = props.limitParam
            +"""Limit"""
          }
          input(classes = "focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7 pr-12 sm:text-sm border-gray-300 rounded-md") {
            type = InputType.number
            name = props.limitParam
            id = props.limitParam
            placeholder = "5"
          }
          input(classes = "bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center") {
            type = InputType.submit
          }
        }
        // table data
        StringTable(props.toStringTableProps())
      }
    }
  }
}
