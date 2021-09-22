package xyz.adrw.hotwire.html.components.tailwinds

import xyz.adrw.hotwire.html.hotwire.turbo_frame
import xyz.adrw.hotwire.html.infra.Link
import xyz.adrw.hotwire.html.infra.template
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.id

data class NavbarMobileMenuProps(
  val visible: Boolean = false,
  val links: List<Link> = listOf(
    Link("Dashboard", "#", true),
    Link("Team", "#"),
    Link("Projects", "#"),
    Link("Calendar", "#"),
  )
)

val NavbarMobileMenuId = "mobile_menu_frame"

val NavbarMobileMenu = template<NavbarMobileMenuProps> { props ->
  val visibleClass = if (props.visible) {
    "sm:hidden"
  } else {
    "hidden"
  }
  turbo_frame(NavbarMobileMenuId) {

    div(classes = visibleClass) {
      id = "mobile-menu"
      div("px-2 pt-2 pb-3 space-y-1") {
//        +"""<!-- Current: "bg-gray-900 text-white", Default: "text-gray-300 hover:bg-gray-700 hover:text-white" -->"""
        props.links.map { link ->
          if (link.isSelected) {
            a(classes = "bg-gray-900 text-white block px-3 py-2 rounded-md text-base font-medium") {
              href = link.href
              attributes["aria-current"] = "page"
              +link.label
            }
          } else {
            a(classes = "text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium") {
              href = link.href
              +link.label
            }
          }
        }
      }
    }
  }
}
