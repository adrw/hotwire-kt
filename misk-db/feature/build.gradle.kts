plugins {
  `java-library`
  id("app.cash.sqldelight")
  id("com.squareup.wire")
}

dependencies {
  // Basic Misk libraries
  api(libs.miskActions)
  api(libs.miskAdmin)
  api(libs.miskFeature)
  api(libs.miskInject)
  api(libs.wispDeployment)
  api(libs.wispLogging)
  api(libs.wispMoshi)
  implementation(libs.miskCore)
  implementation(libs.miskService)

  // Database
  implementation(libs.miskJdbc)
  implementation(libs.sqldelightJdbc)
  implementation(libs.sqldelightJdbcDriver)

  // ui
  api(projects.hotwireKt.kotlinxHtmlTemplates)
  api(projects.hotwireKt.tailwindsUi)

  // testing
  testImplementation(projects.miskDb.featureSample)

  testImplementation(libs.junitApi)
  testImplementation(libs.kotlinTest)
  testImplementation(libs.misk)
  testImplementation(libs.miskAdmin)
  testImplementation(testFixtures(libs.miskJdbc))
  testImplementation(libs.miskTesting)

  testRuntimeOnly(libs.junitEngine)
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
      dialect(libs.sqldelightMysqlDialect)
      packageName.set("misk.db")
      srcDirs(listOf("src/main/sqldelight", "src/main/sqldelight-migrations"))
      deriveSchemaFromMigrations.set(true)
    }
  }
}

val compileKotlin by tasks.getting {
  dependsOn("generateMainFeatureDatabaseInterface")
}
