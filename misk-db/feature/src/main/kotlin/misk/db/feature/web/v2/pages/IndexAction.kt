package misk.db.feature.web.v2.pages

import kotlinx.html.ButtonType
import kotlinx.html.a
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
import misk.db.feature.web.PathBuilder
import misk.db.feature.web.v2.frames.InlineEditAction
import misk.db.feature.web.v2.frames.InlineEditAction.Companion.NEW_FEATURE_NAME
import misk.db.protos.feature.GetFeaturesRequest
import misk.web.Get
import misk.web.QueryParam
import misk.web.ResponseContentType
import misk.web.actions.WebAction
import misk.web.dashboard.AdminDashboardAccess
import misk.web.mediatype.MediaTypes
import misk.web.v2.DashboardPageLayout
import wisp.feature.Attributes
import xyz.adrw.hotwire.templates.turbo_frame
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Singleton
class IndexAction @Inject private constructor(
  private val api: InternalApi,
  private val dashboardPageLayout: DashboardPageLayout,
) : WebAction {
  @Get("/_admin/feature/")
  @ResponseContentType(MediaTypes.TEXT_HTML)
  @AdminDashboardAccess
  fun get(
    @QueryParam q: String?,
  ): String = dashboardPageLayout
    .newBuilder()
    .build { appName, _, _ ->
      val features = api.GetFeatures(GetFeaturesRequest(search_query = q)).features
      div("px-4 py-2") {
        h1("text-3xl") { +"""Feature""" }
        div("flex justify-between py-2.5") {
          p("""text-sm""") { +"""${features.size} flags""" }
          a(classes = "rounded-md bg-white px-5 py-1.5 text-sm font-semibold text-green-900 shadow-sm ring-1 ring-inset ring-green-300 hover:bg-green-50 sm:block") {
            href = PathBuilder(path = InlineEditAction.PATH, query = NEW_FEATURE_NAME).build()
            +"""Create"""
          }
        }
        ul("divide-y divide-gray-100") {
          role = "list"

          // Add a row for a new flag create form
          turbo_frame("feature-${NEW_FEATURE_NAME}") {
            span {}
          }

          features.map { feature ->
            turbo_frame("feature-${feature.name}") {
              this@ul.li("flex items-center justify-between gap-x-6 py-5") {
                div("min-w-0") {
                  div("flex items-start gap-x-3") {
                    p("text-base font-mono font-semibold leading-6 text-gray-900") { +feature.name }

                    feature.config?.rules?.forEachIndexed { index, rule ->
                      p("rounded-md mt-0.5 px-1.5 py-0.5 text-xs font-medium ring-1 ring-inset text-gray-600 bg-gray-50 ring-gray-500/10") {
                        val type = feature.config.getFeatureClazz()
                        val indexPrefix =
                          if (feature.config.rules.size > 1) "${index + 1} - " else ""
                        val evaluatedValue =
                          feature.config.evaluate(type, feature.name, Attributes())
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
                          (Instant.now().epochSecond - instant.epochSecond).toDuration(
                            DurationUnit.SECONDS
                          )
                        val truncatedAgoDuration = if (agoDuration.inWholeHours > 24) {
                          val value = agoDuration.inWholeDays
                          if (value > 1) "$value days" else "$value day"
                        } else if (agoDuration.inWholeMinutes > 60) {
                          val value = agoDuration.inWholeHours
                          if (value > 1) "$value hours" else "$value hour"
                        } else if (agoDuration.inWholeSeconds > 60) {
                          val value = agoDuration.inWholeMinutes
                          if (value > 1) "$value mins" else "$value min"
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
                  a(classes = "rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:block") {
                    href = PathBuilder(path = InlineEditAction.PATH, query = feature.name).build()
                    attributes["data-turbo"] = "true"
                    +"""Edit"""
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

    }
}
