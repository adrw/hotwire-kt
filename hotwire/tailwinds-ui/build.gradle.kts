import hotwire_kt.Dependencies

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jetbrains.kotlin.plugin.allopen")
}

dependencies {
  api(projects.hotwire.kotlinxHtmlTemplates)
  implementation(Dependencies.kotlinxHtml)
}
