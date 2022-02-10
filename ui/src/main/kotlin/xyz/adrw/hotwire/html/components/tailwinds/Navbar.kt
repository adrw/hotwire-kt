package xyz.adrw.hotwire.html.components.tailwinds

import xyz.adrw.hotwire.html.infra.template
import kotlinx.html.*

data class NavbarProps(
  val mobileMenuIsOpen: Boolean = false
)

val Navbar = template<NavbarProps> { props ->
  nav("bg-gray-800") {
    div("max-w-7xl mx-auto px-2 sm:px-6 lg:px-8") {
      div("relative flex items-center justify-between h-16") {
        div("absolute inset-y-0 left-0 flex items-center sm:hidden") {
//          +"""<!-- Mobile menu button-->"""
          form {
            action="/app/navbar-mobile-menu-toggle"
            attributes["method"]="post"

            input {
              type=InputType.hidden
              id="current"
              name="current"
              value=props.mobileMenuIsOpen.toString()
            }

            button(classes = "inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white") {
              type = ButtonType.button
              attributes["aria-controls"] = "mobile-menu"
              attributes["aria-expanded"] = "false"
              span("sr-only") { +"""Open main menu""" }

              val iconClass = if (props.mobileMenuIsOpen) {
                "fa-times"
              } else {
                "fa-bars"
              }

              i("fas $iconClass")

//            +"""<!--
//            Icon when menu is closed.
//
//            Heroicon name: outline/menu
//
//            Menu open: "hidden", Menu closed: "block"
//          -->"""
//            svg("block h-6 w-6") {
//              attributes["xmlns"] = "http://www.w3.org/2000/svg"
//              attributes["fill"] = "none"
//              attributes["viewbox"] = "0 0 24 24"
//              attributes["stroke"] = "currentColor"
//              attributes["aria-hidden"] = "true"
//
//              path {
//                attributes["stroke-linecap"] = "round"
//                attributes["stroke-linejoin"] = "round"
//                attributes["stroke-width"] = "2"
//                attributes["d"] = "M4 6h16M4 12h16M4 18h16"
//              }
//            }
//            +"""<!--
//            Icon when menu is open.
//
//            Heroicon name: outline/x
//
//            Menu open: "block", Menu closed: "hidden"
//          -->"""
              // TODO handle SVG
//            svg("hidden h-6 w-6") {
//              xmlns = "http://www.w3.org/2000/svg"
//              fill = "none"
//              viewbox = "0 0 24 24"
//              stroke = "currentColor"
//              attributes["aria-hidden"] = "true"
//              path {
//                attributes["stroke-linecap"] = "round"
//                attributes["stroke-linejoin"] = "round"
//                attributes["stroke-width"] = "2"
//                d = "M6 18L18 6M6 6l12 12"
//              }
//            }
            }
          }


        }
        div("flex-1 flex items-center justify-center sm:items-stretch sm:justify-start") {
          div("flex-shrink-0 flex items-center") {
            img(classes = "block lg:hidden h-8 w-auto") {
              src = "https://tailwindui.com/img/logos/workflow-mark-indigo-500.svg"
              alt = "Workflow"
            }
            img(classes = "hidden lg:block h-8 w-auto") {
              src = "https://tailwindui.com/img/logos/workflow-logo-indigo-500-mark-white-text.svg"
              alt = "Workflow"
            }
          }
          div("hidden sm:block sm:ml-6") {
            div("flex space-x-4") {

//              +"""<!-- Current: "bg-gray-900 text-white", Default: "text-gray-300 hover:bg-gray-700 hover:text-white" -->"""
              a(classes = "bg-gray-900 text-white px-3 py-2 rounded-md text-sm font-medium") {
                href = "#"
                attributes["aria-current"] = "page"
                +"""Dashboard"""
              }
              a(classes = "text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium") {
                href = "#"
                +"""Team"""
              }
              a(classes = "text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium") {
                href = "#"
                +"""Projects"""
              }
              a(classes = "text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium") {
                href = "#"
                +"""Calendar"""
              }
            }
          }
        }
        div("absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0") {
          button(classes = "bg-gray-800 p-1 rounded-full text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white") {
            type = ButtonType.button
            span("sr-only") { +"""View notifications""" }
//            +"""<!-- Heroicon name: outline/bell -->"""
            // TODO handle SVG
//            svg("h-6 w-6") {
//              attributes["xmlns"] = "http://www.w3.org/2000/svg"
//              attributes["fill"] = "none"
//              attributes["viewbox"] = "0 0 24 24"
//              attributes["stroke"] = "currentColor"
//              attributes["aria-hidden"] = "true"
//
//              path {
//                attributes["stroke-linecap"] = "round"
//                attributes["stroke-linejoin"] = "round"
//                attributes["stroke-width"] = "2"
//                d =
//                  "M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"
//              }
//            }
          }
          NavbarAvatarMenu(NavbarAvatarMenuProps())
        }
      }
    }
    NavbarMobileMenu(NavbarMobileMenuProps())
  }
}
