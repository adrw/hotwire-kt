import hotwire_kt.Dependencies

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jetbrains.kotlin.plugin.allopen")
}

dependencies {
  api(Dependencies.kotlinxHtml)
  api(Dependencies.miskActions)
}
