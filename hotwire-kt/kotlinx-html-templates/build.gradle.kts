plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jetbrains.kotlin.plugin.allopen")
}

dependencies {
  api(libs.kotlinxHtml)
  api(libs.miskActions)
}
