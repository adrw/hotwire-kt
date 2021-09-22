package xyz.adrw.hotwire.html

import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.header
import xyz.adrw.hotwire.html.components.Wrapper
import xyz.adrw.hotwire.html.components.tailwinds.Navbar
import xyz.adrw.hotwire.html.components.tailwinds.NavbarProps
import xyz.adrw.hotwire.html.components.tailwinds.TableWithQuery
import xyz.adrw.hotwire.html.components.tailwinds.TableWithQueryProps
import xyz.adrw.hotwire.html.infra.buildHtml

object Pages {
  fun routes(): Map<String, String> {
    return mapOf(
      DASHBOARD_ROUTE to DASHBOARD_HOME
    )
  }

  val DASHBOARD_ROUTE = "dashboard"
  val DASHBOARD_HOME = buildHtml {
    Wrapper("") {
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
          TableWithQuery(TableWithQueryProps())
        }
      }
    }
  }
}
