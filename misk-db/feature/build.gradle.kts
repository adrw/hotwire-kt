import hotwire_kt.Dependencies

plugins {
  `java-library`
  id("app.cash.sqldelight")
  id("com.squareup.wire")
}

dependencies {
  // Basic Misk libraries
  api(Dependencies.miskActions)
  api(Dependencies.miskAdmin)
  api(Dependencies.miskFeature)
  api(Dependencies.miskInject)
  api(Dependencies.wispDeployment)
  api(Dependencies.wispLogging)
  api(Dependencies.wispMoshi)
  implementation(Dependencies.miskCore)
  implementation(Dependencies.miskService)

  // Database
  implementation(Dependencies.miskJdbc)
  implementation(Dependencies.sqldelightJdbc)
  implementation(Dependencies.sqldelightJdbcDriver)

  // ui
  api(projects.hotwireKt.kotlinxHtmlTemplates)
  api(projects.hotwireKt.tailwindsUi)

  // testing
  testImplementation(projects.miskDb.featureSample)

  testImplementation(Dependencies.junitApi)
  testImplementation(Dependencies.kotlinTest)
  testImplementation(Dependencies.misk)
  testImplementation(Dependencies.miskAdmin)
  testImplementation(testFixtures(Dependencies.miskJdbc))
  testImplementation(Dependencies.miskTesting)

  testRuntimeOnly(Dependencies.junitEngine)
}

sourceSets {
  val main by getting {
    resources.srcDir(
      listOf(
        "web/tabs/feature/lib",
      )
    )
    resources.exclude("**/node_modules")
  }
}

wire {
  protoLibrary = true

  sourcePath {
    srcProject(projects.miskDb.featureProtos)
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
  databases {
    create("FeatureDatabase") {
      dialect(Dependencies.sqldelightMysqlDialect)
      packageName.set("misk.db")
      sourceFolders.set(listOf("sqldelight", "sqldelight-migrations"))
      deriveSchemaFromMigrations.set(true)
    }
  }
}

val compileKotlin by tasks.getting {
  dependsOn("generateMainFeatureDatabaseInterface")
}