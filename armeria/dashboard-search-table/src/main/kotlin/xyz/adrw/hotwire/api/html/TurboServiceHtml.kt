package xyz.adrw.hotwire.api.html

import com.linecorp.armeria.common.QueryParams
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Produces
import wisp.logging.getLogger
import xyz.adrw.hotwire.BooleanParam
import xyz.adrw.hotwire.FakeDatastore.carsData
import xyz.adrw.hotwire.LimitParam
import xyz.adrw.hotwire.PathBuilder
import xyz.adrw.hotwire.ScreenParam
import xyz.adrw.hotwire.SearchParam
import xyz.adrw.hotwire.tailwinds.NavbarAvatarMenu
import xyz.adrw.hotwire.tailwinds.NavbarAvatarMenuId
import xyz.adrw.hotwire.tailwinds.NavbarAvatarMenuProps
import xyz.adrw.hotwire.tailwinds.NavbarMobileMenu
import xyz.adrw.hotwire.tailwinds.NavbarMobileMenuId
import xyz.adrw.hotwire.tailwinds.NavbarMobileMenuProps
import xyz.adrw.hotwire.tailwinds.StringTable
import xyz.adrw.hotwire.tailwinds.StringTableId
import xyz.adrw.hotwire.tailwinds.StringTableProps
import xyz.adrw.hotwire.tailwinds.TableWithQuery
import xyz.adrw.hotwire.tailwinds.TableWithQueryId
import xyz.adrw.hotwire.tailwinds.TableWithQueryProps
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.buildHtml

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
      Wrapper(useCache = false) {
        when (screen) {
          NavbarMobileMenuId -> NavbarMobileMenu(NavbarMobileMenuProps(visible = !currentValue))
          NavbarAvatarMenuId -> NavbarAvatarMenu(NavbarAvatarMenuProps(
            visible = !currentValue,
            buttonPath = PathBuilder(
              screenId = NavbarAvatarMenuId,
              boolean = !currentValue,
            ).build(),
          ))
          StringTableId -> StringTable(StringTableProps(carsData, limit, searchQuery))
          TableWithQueryId -> TableWithQuery(TableWithQueryProps(carsData, limit, searchQuery, PathBuilder(screenId = TableWithQueryId).build(), SearchParam, LimitParam))
          else -> logger.error("GET [screen=$screen] not found")
        }
      }
    }
  }
}
