package xyz.adrw.hotwire.api.html

import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Produces
import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.attributesMapOf
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.input
import kotlinx.html.section
import kotlinx.html.span
import xyz.adrw.hotwire.tailwinds.Wrapper
import xyz.adrw.hotwire.templates.turbo_frame
import xyz.adrw.hotwire.templates.buildHtml

class KotlinxIndexServiceHtml {
  @Get
  @Produces("text/html")
  fun get(): String = buildHtml {
    Wrapper("Home", useCache = false) {
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
        h1 { +"Stimulus" }
        div {
          attributes["data-controller"] = "hello"
          input {
            attributes["data-hello-target"] = "name"
            type = InputType.text
          }
          button {
            attributes["data-action"] = "click->hello#greet"
            +"""Greet"""
          }
          span {
            attributes["data-hello-target"] = "output"
          }
        }
      }
    }
  }
}
