package xyz.adrw.hotwire.html.components.tailwinds

import xyz.adrw.hotwire.html.hotwire.turbo_frame
import xyz.adrw.hotwire.html.infra.template
import kotlinx.html.ButtonType
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.role
import kotlinx.html.span
import xyz.adrw.hotwire.html.components.PathBuilder

data class NavbarAvatarMenuProps(
  val visible: Boolean = false,
  val avatarUrl: String = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
)

val NavbarAvatarMenuId = "avatar_menu_frame"

val NavbarAvatarMenu = template<NavbarAvatarMenuProps> { props ->
  turbo_frame(NavbarAvatarMenuId) {
    //          +"""<!-- Profile dropdown -->"""
    div("ml-3 relative") {
      div {
        a {
          val path = PathBuilder(
            screenId = NavbarAvatarMenuId,
            booleanCurrent = props.visible
          ).build()
          href = path

          button(classes = "bg-gray-800 flex text-sm rounded-full focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white") {
            type = ButtonType.button
            id = "user-menu-button"
            attributes["aria-expanded"] = "false"
            attributes["aria-haspopup"] = "true"
            span("sr-only") { +"""Open user menu""" }
            img(classes = "h-8 w-8 rounded-full") {
              src = props.avatarUrl
              alt = ""
            }
          }
        }
      }

      if (props.visible) {
//            +"""<!--
//            Dropdown menu, show/hide based on menu state.
//
//            Entering: "transition ease-out duration-100"
//              From: "transform opacity-0 scale-95"
//              To: "transform opacity-100 scale-100"
//            Leaving: "transition ease-in duration-75"
//              From: "transform opacity-100 scale-100"
//              To: "transform opacity-0 scale-95"
//          -->"""
        div("origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg py-1 bg-white ring-1 ring-black ring-opacity-5 focus:outline-none") {
          role = "menu"
          attributes["aria-orientation"] = "vertical"
          attributes["aria-labelledby"] = "user-menu-button"
          attributes["tabindex"] = "-1"
//              +"""<!-- Active: "bg-gray-100", Not Active: "" -->"""
          a(classes = "block px-4 py-2 text-sm text-gray-700") {
            href = "#"
            role = "menuitem"
            attributes["tabindex"] = "-1"
            id = "user-menu-item-0"
            +"""Your Profile"""
          }
          a(classes = "block px-4 py-2 text-sm text-gray-700") {
            href = "#"
            role = "menuitem"
            attributes["tabindex"] = "-1"
            id = "user-menu-item-1"
            +"""Settings"""
          }
          a(classes = "block px-4 py-2 text-sm text-gray-700") {
            href = "#"
            role = "menuitem"
            attributes["tabindex"] = "-1"
            id = "user-menu-item-2"
            +"""Sign out"""
          }
        }
      }
    }
  }
}
