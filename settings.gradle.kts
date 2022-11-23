enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("armeria:dashboard-search-table")
include("armeria:full-spec")

include("hotwire:kotlinx-html-templates")
include("hotwire:tailwinds-ui")

include("misk-db:misk-db-feature")
include("misk-db:misk-db-feature-protos")
include("misk-db:misk-db-feature-sample")

// TODO delete and merge usages with above
//include(":kotlinx-html-templates")
//include(":ui")

val localSettings = file("local.settings.gradle.kts")
if (localSettings.exists()) {
  apply(from = localSettings)
}
