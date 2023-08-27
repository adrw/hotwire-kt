package xyz.adrw.hotwire.tailwinds

import kotlinx.html.TagConsumer
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.id
import xyz.adrw.hotwire.templates.Link
import xyz.adrw.hotwire.templates.turbo_frame

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

fun TagConsumer<*>.NavbarMobileMenu(props: NavbarMobileMenuProps) {
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
