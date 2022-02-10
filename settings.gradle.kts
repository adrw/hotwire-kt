rootProject.name="hotwire-kt"

include(":example-dashboard-search-table")
include(":example-full-spec")
include(":kotlinx-html-templates")
include(":ui")

val localSettings = file("local.settings.gradle.kts")
if (localSettings.exists()) {
  apply(from = localSettings)
}
