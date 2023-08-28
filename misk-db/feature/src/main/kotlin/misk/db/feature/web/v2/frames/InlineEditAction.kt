package misk.db.feature.web.v2.frames

import kotlinx.html.ButtonType
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.role
import kotlinx.html.span
import kotlinx.html.time
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
import misk.web.v2.HtmlLayout
import wisp.feature.Attributes
import xyz.adrw.hotwire.templates.buildHtml
import xyz.adrw.hotwire.templates.turbo_frame
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Singleton
class InlineEditAction @Inject private constructor(
  private val api: InternalApi,
) : WebAction {
  @Get(PATH)
  @ResponseContentType(MediaTypes.TEXT_HTML)
  @AdminDashboardAccess
  fun get(
    @QueryParam q: String?,
  ): String = buildHtml {
    HtmlLayout(
      appRoot = "/_admin/",
      title = "",
      playCdn = true,
      headBlock = {}
    ) {
      turbo_frame("feature::$q") {
        val feature = api.GetFeatures(GetFeaturesRequest(q)).features.firstOrNull { it.name == q }

        if (feature == null) {
          div("rounded-md bg-red-50 p-4") {
            div("flex") {
              div("flex-shrink-0") {
                unsafe {
                  raw(
                    """
                    <svg class="h-5 w-5 text-red-400" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                      <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.28 7.22a.75.75 0 00-1.06 1.06L8.94 10l-1.72 1.72a.75.75 0 101.06 1.06L10 11.06l1.72 1.72a.75.75 0 101.06-1.06L11.06 10l1.72-1.72a.75.75 0 00-1.06-1.06L10 8.94 8.28 7.22z" clip-rule="evenodd" />
                    </svg>
                  """.trimIndent()
                  )
                }
              }
              div("ml-3") {
                h3("text-sm font-medium text-red-800") { +"""Feature $q does not exist""" }
                div("mt-2 text-sm text-red-700") {
                  p { +"""This error is unexpected.""" }
                }
              }
            }
          }
        } else {
          li("flex items-center justify-between gap-x-6 py-5") {
            div("min-w-0") {
              div("flex items-start gap-x-3") {
                p("text-base font-mono font-semibold leading-6 text-gray-900") { +feature.name }

                feature.config?.rules?.forEachIndexed { index, rule ->
                  p("rounded-md mt-0.5 px-1.5 py-0.5 text-xs font-medium ring-1 ring-inset text-gray-600 bg-gray-50 ring-gray-500/10") {
                    val type = feature.config.getFeatureClazz()
                    val indexPrefix = if (feature.config.rules.size > 1) "${index + 1} - " else ""
                    val evaluatedValue = feature.config.evaluate(type, feature.name, Attributes())
                    +"$indexPrefix$evaluatedValue"
                  }
                }
              }
              div("mt-1 flex items-center gap-x-2 text-xs leading-5 text-gray-500") {
                p("whitespace-nowrap") {
                  +"""Modified """
                  time {
                    val instant = feature.updated_at ?: Instant.EPOCH
                    val agoDuration =
                      (Instant.now().epochSecond - instant.epochSecond).toDuration(DurationUnit.SECONDS)
                    val truncatedAgoDuration = if (agoDuration.inWholeHours > 24) {
                      "${agoDuration.inWholeDays} days"
                    } else if (agoDuration.inWholeMinutes > 60) {
                      "${agoDuration.inWholeHours} hours"
                    } else if (agoDuration.inWholeSeconds > 60) {
                      "${agoDuration.inWholeMinutes} mins"
                    } else {
                      agoDuration
                    }
                    attributes["datetime"] = instant.toString()
                    +truncatedAgoDuration.toString()
                  }
                  +""" ago"""
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
                p("whitespace-nowrap") {
                  +"""Created on """
                  time {
                    val instant = feature.created_at ?: Instant.EPOCH
                    val formatted = instant.toString().replace("T", " ").replace("Z", " ")
                    attributes["datetime"] = instant.toString()
                    +formatted
                  }
                }
              }
            }
            div("flex flex-none items-center gap-x-4") {
              a(classes = "hidden rounded-md bg-green px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:block") {
                href = "#"
                +"""Save"""
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
      }
    }
  }

  companion object {
    const val PATH = "/frames/feature/edit/"
    const val NEW_FEATURE_NAME = "_NEW_FEATURE_NAME"
  }
}
