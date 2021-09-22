package xyz.adrw.hotwire.api.html

import com.linecorp.armeria.common.QueryParams
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Produces
import xyz.adrw.hotwire.html.components.Wrapper
import xyz.adrw.hotwire.html.components.tailwinds.NavbarAvatarMenu
import xyz.adrw.hotwire.html.components.tailwinds.NavbarAvatarMenuId
import xyz.adrw.hotwire.html.components.tailwinds.NavbarAvatarMenuProps
import xyz.adrw.hotwire.html.components.tailwinds.NavbarMobileMenu
import xyz.adrw.hotwire.html.components.tailwinds.NavbarMobileMenuId
import xyz.adrw.hotwire.html.components.tailwinds.NavbarMobileMenuProps
import xyz.adrw.hotwire.html.components.tailwinds.Table
import xyz.adrw.hotwire.html.components.tailwinds.TableId
import xyz.adrw.hotwire.html.components.tailwinds.TableProps
import xyz.adrw.hotwire.html.components.tailwinds.TableWithQuery
import xyz.adrw.hotwire.html.components.tailwinds.TableWithQueryId
import xyz.adrw.hotwire.html.components.tailwinds.TableWithQueryProps
import xyz.adrw.hotwire.html.infra.buildHtml
import wisp.logging.getLogger
import xyz.adrw.hotwire.FakeDatastore.carsData
import xyz.adrw.hotwire.html.components.BooleanParam
import xyz.adrw.hotwire.html.components.LimitParam
import xyz.adrw.hotwire.html.components.ScreenParam
import xyz.adrw.hotwire.html.components.SearchParam

/**
 * Endpoint that handles interactive UI from Hotwire Turbo Frame related clicks
 * Configuration of which UI to return and input data (ie. from forms) is provided by query parameters
 */
class TurboServiceHtml {
  private val logger = getLogger<TurboServiceHtml>()

  @Get
  @Produces("text/html")
  fun get(params: QueryParams): String {
    val screen = params[ScreenParam]
    val currentValue = params[BooleanParam].toBoolean()
    val searchQuery = params[SearchParam]
    val limit = params[LimitParam]?.toIntOrNull()

    return buildHtml {
      Wrapper("") {
        when (screen) {
          NavbarMobileMenuId -> NavbarMobileMenu(NavbarMobileMenuProps(visible = !currentValue))
          NavbarAvatarMenuId -> NavbarAvatarMenu(NavbarAvatarMenuProps(visible = !currentValue))
          TableId -> Table(TableProps(carsData, limit, searchQuery))
          TableWithQueryId -> TableWithQuery(TableWithQueryProps(carsData, limit, searchQuery))
          else -> logger.error("GET [screen=$screen] not found")
        }
      }
    }
  }
}
