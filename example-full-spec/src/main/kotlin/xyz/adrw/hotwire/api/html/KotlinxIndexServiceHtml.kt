package xyz.adrw.hotwire.api.html

import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Produces
import kotlinx.html.a
import kotlinx.html.attributesMapOf
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.p
import kotlinx.html.section
import xyz.adrw.hotwire.html.components.Wrapper
import xyz.adrw.hotwire.html.hotwire.turbo_frame
import xyz.adrw.hotwire.html.infra.buildHtml

class KotlinxIndexServiceHtml {
  @Get
  @Produces("text/html")
  fun get(): String = buildHtml {
    Wrapper("Home") {
      section {
        h1 { +"Greeting Section" }
        h2 { +"Turbo Drive" }
        div {
          div {
            a("/app/greeting/?person=Josh") { +"Click here to greet Josh (fast)" }
          }
          div {
            a("/app/greeting/?person=Josh&sleep=true") { +"Click here to greet Josh (slow)" }
          }
        }
        h2 { +"Turbo Frame" }
        div {
          turbo_frame("greeting_frame") {
            a("/app/greeting/?person=Josh") { +"Click here to greet Josh" }
          }
        }
        div {
          attributesMapOf("data-turbo", "false")
          h2 {
            +"Regular"
          }
          div {
            turbo_frame("greeting_frame") {
              a("/app/greeting/?person=Josh") { +"Click here to greet Josh (fast)" }
            }
          }
          div {
            a("/app/greeting/?person=Josh&sleep=true") { +"Click here to greet Josh (slow)" }
          }
        }
      }
    }
  }

}
