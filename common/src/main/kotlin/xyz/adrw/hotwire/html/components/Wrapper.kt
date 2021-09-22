package xyz.adrw.hotwire.html.components

import xyz.adrw.hotwire.html.infra.layout
import kotlinx.html.*

val Wrapper = layout<String> { title, template ->
  html {
    head {
      meta { charset = "UTF-8" }
      meta {
        name = "viewport"
        content = "width=device-width, initial-scale=1.0"
      }
      link {
        href = "https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css"
        rel = "stylesheet"
      }
      link {
        rel = "stylesheet"
        href =
          "https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/fontawesome.min.css"
        attributes["integrity"] =
          "sha384-jLKHWM3JRmfMU0A5x5AkjWkw/EYfGUAGagvnfryNV3F9VqM98XiIH7VBGVoxVSc7"
        attributes["crossorigin"] = "anonymous"
      }
      script {
        src = "https://unpkg.com/@hotwired/turbo@7.0.0-beta.3/dist/turbo.es5-umd.js"
      }
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
    }
    body {
      template()
    }
  }
}

