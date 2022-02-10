package xyz.adrw.hotwire.html.components.tailwinds

import xyz.adrw.hotwire.html.hotwire.turbo_frame
import xyz.adrw.hotwire.html.infra.template
import kotlinx.html.InputType
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import xyz.adrw.hotwire.html.components.LimitParam
import xyz.adrw.hotwire.html.components.PathBuilder
import xyz.adrw.hotwire.html.components.SearchParam

data class TableWithQueryProps(
  val data: List<List<String>> = loadingData,
  val limit: Int? = null,
  val query: String? = null,
) {
  fun toTableProps() = TableProps(
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

val TableWithQuery = template<TableWithQueryProps> { props ->
  turbo_frame(TableWithQueryId) {
    div {
      div("mt-1 relative rounded-md shadow-sm") {
//              div("absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none") {
//                span("text-gray-500 sm:text-sm") { +"""Title""" }
//              }
        form {
          val path = PathBuilder(
            screenId = TableWithQueryId,
          ).build()
          action = path
          label("block text-sm font-medium text-gray-700") {
            attributes["htmlFor"] = SearchParam
            +"""Search"""
          }
          input(classes = "focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7 pr-12 sm:text-sm border-gray-300 rounded-md") {
            type = InputType.text
            // appears in path param
            name = SearchParam
            id = SearchParam
            placeholder = "Buick"
          }
          label("block text-sm font-medium text-gray-700") {
            attributes["htmlFor"] = LimitParam
            +"""Limit"""
          }
          input(classes = "focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-7 pr-12 sm:text-sm border-gray-300 rounded-md") {
            type = InputType.number
            name = LimitParam
            id = LimitParam
            placeholder = "5"
          }
          input(classes = "bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded inline-flex items-center") {
            type = InputType.submit
          }
        }
        // table data
        Table(props.toTableProps())
      }
    }
  }
}
