package misk.db.feature.web.v2

import kotlinx.html.ButtonType
import kotlinx.html.a
import kotlinx.html.attributesMapOf
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.role
import kotlinx.html.span
import kotlinx.html.time
import kotlinx.html.ul
import kotlinx.html.unsafe
import misk.db.feature.DbFeatureFlags.Companion.evaluate
import misk.db.feature.getFeatureClazz
import misk.db.feature.web.InternalApi
import misk.db.protos.feature.GetFeaturesRequest
import misk.web.Get
import misk.web.QueryParam
import misk.web.ResponseContentType
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import misk.web.mediatype.MediaTypes
import misk.web.v2.DashboardPageLayout
import wisp.feature.Attributes
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IndexAction @Inject private constructor(
  private val dashboardPageLayout: DashboardPageLayout,
  private val api: InternalApi,
) : WebAction {
  @Get("/_admin/feature/")
  @ResponseContentType(MediaTypes.TEXT_HTML)
  @AdminDashboardAccess
  fun get(
    @QueryParam q: String?,
  ): String = dashboardPageLayout
    .newBuilder()
    .build { appName, _, _ ->
      div("px-4 py-2") {
        h1("text-3xl") { +"""Feature""" }
        ul("divide-y divide-gray-100") {
          role = "list"

          api.GetFeatures(GetFeaturesRequest(search_query = q)).features.map { feature ->
            li("flex items-center justify-between gap-x-6 py-5") {
              div("min-w-0") {
                div("flex items-start gap-x-3") {
                  p("text-sm font-semibold leading-6 text-gray-900") { +feature.name }

                  feature.config?.rules?.forEachIndexed { index, rule ->
                    p("rounded-md mt-0.5 px-1.5 py-0.5 text-xs font-medium ring-1 ring-inset text-green-700 bg-green-50 ring-green-600/20") {
                      val type = feature.config.getFeatureClazz()
                      val indexPrefix = if (feature.config.rules.size > 1) "${index + 1} - " else ""
                      +"$indexPrefix${feature.config.evaluate(type, feature.name, Attributes())}\n"
                    }
                  }
                }
                div("mt-1 flex items-center gap-x-2 text-xs leading-5 text-gray-500") {
                  p("whitespace-nowrap") {
                    +"""Updated on """
                    time {
                      val instant = feature.updated_at ?: Instant.EPOCH
                      val formatted = instant.toString().replace("T", " ").replace("Z", " ")
                      attributes["datetime"] = instant.toString()
                      +formatted
                    }
                  }
                  unsafe {
                    raw(
                      """
                  <svg viewBox="0 0 2 2" class="h-0.5 w-0.5 fill-current">
                    <circle cx="1" cy="1" r="1" />
                  </svg>
                """.trimIndent()
                    )
                  }
                }
              }
              div("flex flex-none items-center gap-x-4") {
                a(classes = "hidden rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:block") {
                  href = "#"
                  +"""Edit"""
                  span("sr-only") { +""", GraphQL API""" }
                }
                div("relative flex-none") {
                  button(classes = "-m-2.5 block p-2.5 text-gray-500 hover:text-gray-900") {
                    type = ButtonType.button
                    id = "options-menu-0-button"
                    attributes["aria-expanded"] = "false"
                    attributes["aria-haspopup"] = "true"
                    span("sr-only") { +"""Open options""" }
                    unsafe {
                      raw(
                        """
                    <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                      <path d="M10 3a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM10 8.5a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM11.5 15.5a1.5 1.5 0 10-3 0 1.5 1.5 0 003 0z" />
                    </svg>
                  """.trimIndent()
                      )
                    }
                  }
//              +"""<!--
//          Dropdown menu, show/hide based on menu state.
//
//          Entering: "transition ease-out duration-100"
//            From: "transform opacity-0 scale-95"
//            To: "transform opacity-100 scale-100"
//          Leaving: "transition ease-in duration-75"
//            From: "transform opacity-100 scale-100"
//            To: "transform opacity-0 scale-95"
//        -->"""
                  div("hidden absolute right-0 z-10 mt-2 w-32 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none") {
                    role = "menu"
                    attributes["aria-orientation"] = "vertical"
                    attributes["aria-labelledby"] = "options-menu-0-button"
                    attributes["tabindex"] = "-1"
//                  +"""<!-- Active: "bg-gray-50", Not Active: "" -->"""
                    a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
                      href = "#"
                      role = "menuitem"
                      attributes["tabindex"] = "-1"
                      id = "options-menu-0-item-0"
                      +"""Edit"""
                      span("sr-only") { +""", GraphQL API""" }
                    }
                    a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
                      href = "#"
                      role = "menuitem"
                      attributes["tabindex"] = "-1"
                      id = "options-menu-0-item-1"
                      +"""Move"""
                      span("sr-only") { +""", GraphQL API""" }
                    }
                    a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
                      href = "#"
                      role = "menuitem"
                      attributes["tabindex"] = "-1"
                      id = "options-menu-0-item-2"
                      +"""Delete"""
                      span("sr-only") { +""", GraphQL API""" }
                    }
                  }
                }
              }
            }
          }

//        li("flex items-center justify-between gap-x-6 py-5") {
//          div("min-w-0") {
//            div("flex items-start gap-x-3") {
//              p("text-sm font-semibold leading-6 text-gray-900") { +"""GraphQL API""" }
//              p("rounded-md whitespace-nowrap mt-0.5 px-1.5 py-0.5 text-xs font-medium ring-1 ring-inset text-green-700 bg-green-50 ring-green-600/20") { +"""Complete""" }
//            }
//            div("mt-1 flex items-center gap-x-2 text-xs leading-5 text-gray-500") {
//              p("whitespace-nowrap") {
//                +"""Due on"""
////                time {
////                  datetime = "2023-03-17T00:00Z"
////                  +"""March 17, 2023"""
////                }
//              }
//              unsafe {
//                raw("""
//                  <svg viewBox="0 0 2 2" class="h-0.5 w-0.5 fill-current">
//                    <circle cx="1" cy="1" r="1" />
//                  </svg>
//                """.trimIndent())
//              }
//              p("truncate") { +"""Created by Leslie Alexander""" }
//            }
//          }
//          div("flex flex-none items-center gap-x-4") {
//            a(classes = "hidden rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:block") {
//              href = "#"
//              +"""View project"""
//              span("sr-only") { +""", GraphQL API""" }
//            }
//            div("relative flex-none") {
//              button(classes = "-m-2.5 block p-2.5 text-gray-500 hover:text-gray-900") {
//                type = ButtonType.button
//                id = "options-menu-0-button"
//                attributes["aria-expanded"] = "false"
//                attributes["aria-haspopup"] = "true"
//                span("sr-only") { +"""Open options""" }
//                unsafe {
//                  raw("""
//                    <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
//                      <path d="M10 3a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM10 8.5a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM11.5 15.5a1.5 1.5 0 10-3 0 1.5 1.5 0 003 0z" />
//                    </svg>
//                  """.trimIndent())
//                }
//              }
////              +"""<!--
////          Dropdown menu, show/hide based on menu state.
////
////          Entering: "transition ease-out duration-100"
////            From: "transform opacity-0 scale-95"
////            To: "transform opacity-100 scale-100"
////          Leaving: "transition ease-in duration-75"
////            From: "transform opacity-100 scale-100"
////            To: "transform opacity-0 scale-95"
////        -->"""
//              div("absolute right-0 z-10 mt-2 w-32 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none") {
//                role = "menu"
//                attributes["aria-orientation"] = "vertical"
//                attributes["aria-labelledby"] = "options-menu-0-button"
//                attributes["tabindex"] = "-1"
//                +"""<!-- Active: "bg-gray-50", Not Active: "" -->"""
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-0-item-0"
//                  +"""Edit"""
//                  span("sr-only") { +""", GraphQL API""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-0-item-1"
//                  +"""Move"""
//                  span("sr-only") { +""", GraphQL API""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-0-item-2"
//                  +"""Delete"""
//                  span("sr-only") { +""", GraphQL API""" }
//                }
//              }
//            }
//          }
//        }
//        li("flex items-center justify-between gap-x-6 py-5") {
//          div("min-w-0") {
//            div("flex items-start gap-x-3") {
//              p("text-sm font-semibold leading-6 text-gray-900") { +"""New benefits plan""" }
//              p("rounded-md whitespace-nowrap mt-0.5 px-1.5 py-0.5 text-xs font-medium ring-1 ring-inset text-gray-600 bg-gray-50 ring-gray-500/10") { +"""In progress""" }
//            }
//            div("mt-1 flex items-center gap-x-2 text-xs leading-5 text-gray-500") {
//              p("whitespace-nowrap") {
//                +"""Due on"""
////                time {
////                  datetime = "2023-05-05T00:00Z"
////                  +"""May 5, 2023"""
////                }
//              }
//
//              svg("h-0.5 w-0.5 fill-current") {
//                viewbox = "0 0 2 2"
//                circle {
//                  cx = "1"
//                  cy = "1"
//                  r = "1"
//                }
//              }
//              p("truncate") { +"""Created by Leslie Alexander""" }
//            }
//          }
//          div("flex flex-none items-center gap-x-4") {
//            a(classes = "hidden rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:block") {
//              href = "#"
//              +"""View project"""
//              span("sr-only") { +""", New benefits plan""" }
//            }
//            div("relative flex-none") {
//              button(classes = "-m-2.5 block p-2.5 text-gray-500 hover:text-gray-900") {
//                type = ButtonType.button
//                id = "options-menu-1-button"
//                attributes["aria-expanded"] = "false"
//                attributes["aria-haspopup"] = "true"
//                span("sr-only") { +"""Open options""" }
//                svg("h-5 w-5") {
//                  viewbox = "0 0 20 20"
//                  fill = "currentColor"
//                  attributes["aria-hidden"] = "true"
//                  path {
//                    d =
//                      "M10 3a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM10 8.5a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM11.5 15.5a1.5 1.5 0 10-3 0 1.5 1.5 0 003 0z"
//                  }
//                }
//              }
//              +"""<!--
//          Dropdown menu, show/hide based on menu state.
//
//          Entering: "transition ease-out duration-100"
//            From: "transform opacity-0 scale-95"
//            To: "transform opacity-100 scale-100"
//          Leaving: "transition ease-in duration-75"
//            From: "transform opacity-100 scale-100"
//            To: "transform opacity-0 scale-95"
//        -->"""
//              div("absolute right-0 z-10 mt-2 w-32 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none") {
//                role = "menu"
//                attributes["aria-orientation"] = "vertical"
//                attributes["aria-labelledby"] = "options-menu-1-button"
//                attributes["tabindex"] = "-1"
//                +"""<!-- Active: "bg-gray-50", Not Active: "" -->"""
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-1-item-0"
//                  +"""Edit"""
//                  span("sr-only") { +""", New benefits plan""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-1-item-1"
//                  +"""Move"""
//                  span("sr-only") { +""", New benefits plan""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-1-item-2"
//                  +"""Delete"""
//                  span("sr-only") { +""", New benefits plan""" }
//                }
//              }
//            }
//          }
//        }
//        li("flex items-center justify-between gap-x-6 py-5") {
//          div("min-w-0") {
//            div("flex items-start gap-x-3") {
//              p("text-sm font-semibold leading-6 text-gray-900") { +"""Onboarding emails""" }
//              p("rounded-md whitespace-nowrap mt-0.5 px-1.5 py-0.5 text-xs font-medium ring-1 ring-inset text-gray-600 bg-gray-50 ring-gray-500/10") { +"""In progress""" }
//            }
//            div("mt-1 flex items-center gap-x-2 text-xs leading-5 text-gray-500") {
//              p("whitespace-nowrap") {
//                +"""Due on"""
//                time {
//                  datetime = "2023-05-25T00:00Z"
//                  +"""May 25, 2023"""
//                }
//              }
//              svg("h-0.5 w-0.5 fill-current") {
//                viewbox = "0 0 2 2"
//                circle {
//                  cx = "1"
//                  cy = "1"
//                  r = "1"
//                }
//              }
//              p("truncate") { +"""Created by Courtney Henry""" }
//            }
//          }
//          div("flex flex-none items-center gap-x-4") {
//            a(classes = "hidden rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:block") {
//              href = "#"
//              +"""View project"""
//              span("sr-only") { +""", Onboarding emails""" }
//            }
//            div("relative flex-none") {
//              button(classes = "-m-2.5 block p-2.5 text-gray-500 hover:text-gray-900") {
//                type = ButtonType.button
//                id = "options-menu-2-button"
//                attributes["aria-expanded"] = "false"
//                attributes["aria-haspopup"] = "true"
//                span("sr-only") { +"""Open options""" }
//                svg("h-5 w-5") {
//                  viewbox = "0 0 20 20"
//                  fill = "currentColor"
//                  attributes["aria-hidden"] = "true"
//                  path {
//                    d =
//                      "M10 3a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM10 8.5a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM11.5 15.5a1.5 1.5 0 10-3 0 1.5 1.5 0 003 0z"
//                  }
//                }
//              }
//              +"""<!--
//          Dropdown menu, show/hide based on menu state.
//
//          Entering: "transition ease-out duration-100"
//            From: "transform opacity-0 scale-95"
//            To: "transform opacity-100 scale-100"
//          Leaving: "transition ease-in duration-75"
//            From: "transform opacity-100 scale-100"
//            To: "transform opacity-0 scale-95"
//        -->"""
//              div("absolute right-0 z-10 mt-2 w-32 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none") {
//                role = "menu"
//                attributes["aria-orientation"] = "vertical"
//                attributes["aria-labelledby"] = "options-menu-2-button"
//                attributes["tabindex"] = "-1"
//                +"""<!-- Active: "bg-gray-50", Not Active: "" -->"""
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-2-item-0"
//                  +"""Edit"""
//                  span("sr-only") { +""", Onboarding emails""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-2-item-1"
//                  +"""Move"""
//                  span("sr-only") { +""", Onboarding emails""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-2-item-2"
//                  +"""Delete"""
//                  span("sr-only") { +""", Onboarding emails""" }
//                }
//              }
//            }
//          }
//        }
//        li("flex items-center justify-between gap-x-6 py-5") {
//          div("min-w-0") {
//            div("flex items-start gap-x-3") {
//              p("text-sm font-semibold leading-6 text-gray-900") { +"""iOS app""" }
//              p("rounded-md whitespace-nowrap mt-0.5 px-1.5 py-0.5 text-xs font-medium ring-1 ring-inset text-gray-600 bg-gray-50 ring-gray-500/10") { +"""In progress""" }
//            }
//            div("mt-1 flex items-center gap-x-2 text-xs leading-5 text-gray-500") {
//              p("whitespace-nowrap") {
//                +"""Due on"""
//                time {
//                  datetime = "2023-06-07T00:00Z"
//                  +"""June 7, 2023"""
//                }
//              }
//              svg("h-0.5 w-0.5 fill-current") {
//                viewbox = "0 0 2 2"
//                circle {
//                  cx = "1"
//                  cy = "1"
//                  r = "1"
//                }
//              }
//              p("truncate") { +"""Created by Leonard Krasner""" }
//            }
//          }
//          div("flex flex-none items-center gap-x-4") {
//            a(classes = "hidden rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:block") {
//              href = "#"
//              +"""View project"""
//              span("sr-only") { +""", iOS app""" }
//            }
//            div("relative flex-none") {
//              button(classes = "-m-2.5 block p-2.5 text-gray-500 hover:text-gray-900") {
//                type = ButtonType.button
//                id = "options-menu-3-button"
//                attributes["aria-expanded"] = "false"
//                attributes["aria-haspopup"] = "true"
//                span("sr-only") { +"""Open options""" }
//                svg("h-5 w-5") {
//                  viewbox = "0 0 20 20"
//                  fill = "currentColor"
//                  attributes["aria-hidden"] = "true"
//                  path {
//                    d =
//                      "M10 3a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM10 8.5a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM11.5 15.5a1.5 1.5 0 10-3 0 1.5 1.5 0 003 0z"
//                  }
//                }
//              }
//              +"""<!--
//          Dropdown menu, show/hide based on menu state.
//
//          Entering: "transition ease-out duration-100"
//            From: "transform opacity-0 scale-95"
//            To: "transform opacity-100 scale-100"
//          Leaving: "transition ease-in duration-75"
//            From: "transform opacity-100 scale-100"
//            To: "transform opacity-0 scale-95"
//        -->"""
//              div("absolute right-0 z-10 mt-2 w-32 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none") {
//                role = "menu"
//                attributes["aria-orientation"] = "vertical"
//                attributes["aria-labelledby"] = "options-menu-3-button"
//                attributes["tabindex"] = "-1"
//                +"""<!-- Active: "bg-gray-50", Not Active: "" -->"""
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-3-item-0"
//                  +"""Edit"""
//                  span("sr-only") { +""", iOS app""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-3-item-1"
//                  +"""Move"""
//                  span("sr-only") { +""", iOS app""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-3-item-2"
//                  +"""Delete"""
//                  span("sr-only") { +""", iOS app""" }
//                }
//              }
//            }
//          }
//        }
//        li("flex items-center justify-between gap-x-6 py-5") {
//          div("min-w-0") {
//            div("flex items-start gap-x-3") {
//              p("text-sm font-semibold leading-6 text-gray-900") { +"""Marketing site redesign""" }
//              p("rounded-md whitespace-nowrap mt-0.5 px-1.5 py-0.5 text-xs font-medium ring-1 ring-inset text-yellow-800 bg-yellow-50 ring-yellow-600/20") { +"""Archived""" }
//            }
//            div("mt-1 flex items-center gap-x-2 text-xs leading-5 text-gray-500") {
//              p("whitespace-nowrap") {
//                +"""Due on"""
//                time {
//                  datetime = "2023-06-10T00:00Z"
//                  +"""June 10, 2023"""
//                }
//              }
//              svg("h-0.5 w-0.5 fill-current") {
//                viewbox = "0 0 2 2"
//                circle {
//                  cx = "1"
//                  cy = "1"
//                  r = "1"
//                }
//              }
//              p("truncate") { +"""Created by Courtney Henry""" }
//            }
//          }
//          div("flex flex-none items-center gap-x-4") {
//            a(classes = "hidden rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:block") {
//              href = "#"
//              +"""View project"""
//              span("sr-only") { +""", Marketing site redesign""" }
//            }
//            div("relative flex-none") {
//              button(classes = "-m-2.5 block p-2.5 text-gray-500 hover:text-gray-900") {
//                type = ButtonType.button
//                id = "options-menu-4-button"
//                attributes["aria-expanded"] = "false"
//                attributes["aria-haspopup"] = "true"
//                span("sr-only") { +"""Open options""" }
//                svg("h-5 w-5") {
//                  viewbox = "0 0 20 20"
//                  fill = "currentColor"
//                  attributes["aria-hidden"] = "true"
//                  path {
//                    d =
//                      "M10 3a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM10 8.5a1.5 1.5 0 110 3 1.5 1.5 0 010-3zM11.5 15.5a1.5 1.5 0 10-3 0 1.5 1.5 0 003 0z"
//                  }
//                }
//              }
//              +"""<!--
//          Dropdown menu, show/hide based on menu state.
//
//          Entering: "transition ease-out duration-100"
//            From: "transform opacity-0 scale-95"
//            To: "transform opacity-100 scale-100"
//          Leaving: "transition ease-in duration-75"
//            From: "transform opacity-100 scale-100"
//            To: "transform opacity-0 scale-95"
//        -->"""
//              div("absolute right-0 z-10 mt-2 w-32 origin-top-right rounded-md bg-white py-2 shadow-lg ring-1 ring-gray-900/5 focus:outline-none") {
//                role = "menu"
//                attributes["aria-orientation"] = "vertical"
//                attributes["aria-labelledby"] = "options-menu-4-button"
//                attributes["tabindex"] = "-1"
//                +"""<!-- Active: "bg-gray-50", Not Active: "" -->"""
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-4-item-0"
//                  +"""Edit"""
//                  span("sr-only") { +""", Marketing site redesign""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-4-item-1"
//                  +"""Move"""
//                  span("sr-only") { +""", Marketing site redesign""" }
//                }
//                a(classes = "block px-3 py-1 text-sm leading-6 text-gray-900") {
//                  href = "#"
//                  role = "menuitem"
//                  attributes["tabindex"] = "-1"
//                  id = "options-menu-4-item-2"
//                  +"""Delete"""
//                  span("sr-only") { +""", Marketing site redesign""" }
//                }
//              }
//            }
//          }
//        }
        }
      }
    }
}
