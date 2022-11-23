package xyz.adrw.hotwire.html

import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.header
import xyz.adrw.hotwire.LimitParam
import xyz.adrw.hotwire.PathBuilder
import xyz.adrw.hotwire.SearchParam
import xyz.adrw.hotwire.tailwinds.Navbar
import xyz.adrw.hotwire.tailwinds.NavbarProps
import xyz.adrw.hotwire.tailwinds.TableWithQuery
import xyz.adrw.hotwire.tailwinds.TableWithQueryId
import xyz.adrw.hotwire.tailwinds.TableWithQueryProps
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.buildHtml

object Pages {
  fun routes(): Map<String, String> {
    return mapOf(
      DASHBOARD_ROUTE to DASHBOARD_HOME
    )
  }

  val DASHBOARD_ROUTE = "dashboard"
  val DASHBOARD_HOME = buildHtml {
    Wrapper(useCache = false) {
      Navbar(NavbarProps())

      // Navbar page
      header("bg-white shadow") {
        div("max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8") {
          h1("text-3xl font-bold text-gray-900") { +"Cars" }
        }
      }

      // <main>
      div {
        div("max-w-7xl mx-auto py-6 sm:px-6 lg:px-8") {
          TableWithQuery(TableWithQueryProps(formPath = PathBuilder(screenId = TableWithQueryId).build(), searchParam = SearchParam, limitParam = LimitParam))
        }
      }
    }
  }
}
