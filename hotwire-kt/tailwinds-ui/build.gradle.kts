plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jetbrains.kotlin.plugin.allopen")
}

dependencies {
  api(projects.hotwireKt.kotlinxHtmlTemplates)
  implementation(libs.kotlinxHtml)
}
