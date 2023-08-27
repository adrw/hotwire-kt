package xyz.adrw.hotwire.tailwinds

import kotlinx.html.TagConsumer
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.header

data class NavbarPageProps(
  val title: String,
  val header: String,
  val homePath: String,
  val searchPath: String,
  val profilePath: String,
  val buttonPath: String,
  val loginPath: String,
)

val NavbarPageContentFrameId = "content"

fun TagConsumer<*>.NavbarPage(props: NavbarPageProps, content: TagConsumer<*>.() -> Unit) {
  Wrapper(props.title, {}) {
    Navbar(NavbarProps(
      homePath = props.homePath,
      searchPath = props.searchPath,
      profilePath = props.profilePath,
      buttonPath = props.buttonPath,
      loginPath = props.loginPath,
    ))
    header("bg-white shadow") {
      div("max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8") {
        h1("text-3xl font-bold text-gray-900") { +props.header }
          content()
      }
    }
  }
}
