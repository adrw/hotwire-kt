package xyz.adrw.hotwire.tailwinds

import kotlinx.html.TagConsumer
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.script
import kotlinx.html.title

fun TagConsumer<*>.Wrapper(
  title: String = "",
  headBlock: TagConsumer<*>.() -> Unit = {},
  useCache: Boolean = true,
  bodyBlock: TagConsumer<*>.() -> Unit
) {
  html {
    head {
      meta { charset = "UTF-8" }
      meta {
        name = "viewport"
        content = "width=device-width, initial-scale=1.0"
      }
      link {
        href = if (useCache) {
          "/static/cache/tailwind.min.css"
        } else {
          "https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css"
        }
        rel = "stylesheet"
      }
      link {
        rel = "stylesheet"
        href = if (useCache) {
          "/static/cache/fontawesome.min.css"
        } else {
          "https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/fontawesome.min.css"
        }
        attributes["integrity"] =
          "sha384-jLKHWM3JRmfMU0A5x5AkjWkw/EYfGUAGagvnfryNV3F9VqM98XiIH7VBGVoxVSc7"
        attributes["crossorigin"] = "anonymous"
      }
      script {
        src = if (useCache) {
          "/static/cache/turbo.es5-umd.js"
        } else {
          "https://unpkg.com/@hotwired/turbo@7.0.0-beta.3/dist/turbo.es5-umd.js"
        }
      }

//      TODO set a root so Turbo navigation can properly determine when full reloads are necessary
//      meta {
//        name = "turbo-root"
//        content = "/app"
//      }
      title(title)
      script {
        type = "text/javascript"
        """
        |if (window["EventSource"] && window["Turbo"]) {
        |   Turbo.connectStreamSource(new EventSource("/load"));
        |} else {
        |    console.warn("Turbo Streams over SSE not available");
        |}
      """.trimMargin()
      }
      headBlock()
    }
    body {
      div {
        bodyBlock()
      }
    }
  }
}


