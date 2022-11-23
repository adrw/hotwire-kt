import hotwire_kt.Dependencies

plugins {
  `java-library`
  id("com.squareup.sqldelight")
  id("com.squareup.wire")
}

dependencies {
  // Basic Misk libraries
  api(Dependencies.miskActions)
  api(Dependencies.miskAdmin)
  api(Dependencies.miskFeature)
  api(Dependencies.miskInject)
  api(Dependencies.wispDeployment)
  api(Dependencies.wispMoshi)
  implementation(Dependencies.miskCore)
  implementation(Dependencies.miskService)

  // Database
  implementation(Dependencies.sqldelightJdbcDriver)

  // ui
  api(projects.hotwire.kotlinxHtmlTemplates)
  api(projects.hotwire.tailwindsUi)

  // testing
  testImplementation(projects.miskDb.miskDbFeatureSample)

  testImplementation(Dependencies.junitApi)
  testImplementation(Dependencies.kotlinTest)
  testImplementation(Dependencies.misk)
  testImplementation(Dependencies.miskJdbcTesting)
  testImplementation(Dependencies.miskTesting)

  testRuntimeOnly(Dependencies.junitEngine)
}

sourceSets {
  val main by getting {
    resources.srcDir(listOf(
      "web/tabs/feature/lib",
    ))
    resources.exclude("**/node_modules")
  }
}

wire {
  protoLibrary = true

  sourcePath {
    srcProject(projects.miskDb.miskDbFeatureProtos)
  }

  kotlin {
    includes = listOf(
      "misk.db.protos.feature.FeatureService",
    )
    rpcCallStyle = "blocking"
    rpcRole = "server"
    singleMethodServices = true
  }
  kotlin {
    javaInterop = true
  }
}

sqldelight {
  database("FeatureDatabase") {
    packageName = "misk.db"
    sourceFolders = listOf("sqldelight", "sqldelight-migrations")
    deriveSchemaFromMigrations = true
    dialect = "mysql"
  }
}

val compileKotlin by tasks.getting {
  dependsOn("generateMainFeatureDatabaseInterface")
}