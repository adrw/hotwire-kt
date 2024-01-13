plugins {
  id("app.cash.sqldelight")
  id("com.squareup.wire")
  application
  id("com.github.johnrengelman.shadow")
}

val applicationMainClass = "misk.db.flagpole.FlagpoleServiceKt"
application {
  mainClass.set(applicationMainClass)
}

dependencies {
  implementation(projects.miskDb.feature)

  implementation(libs.logbackClassic)
  implementation(libs.misk)
  implementation(libs.miskAdmin)
  implementation(libs.miskCore)
  implementation(libs.miskFeature)
  implementation(libs.miskInject)
  implementation(libs.wispConfig)

  // Database
  implementation(libs.miskJdbc)
  implementation(libs.sqldelightJdbc)
  implementation(libs.sqldelightJdbcDriver)
}

wire {
  protoLibrary = true

  sourcePath("src/main/proto")

  kotlin {
    includes = listOf(
      "misk.db.protos.flagpole.FlagpoleApiService",
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
    create("FlagpoleDatabase") {
      dialect(libs.sqldelightMysqlDialect)
      packageName.set("misk.db.flagpole.db")
      srcDirs(listOf("src/main/sqldelight", "src/main/resources/db-migrations"))
      deriveSchemaFromMigrations.set(true)
      migrationOutputDirectory.set(file("$buildDir/resources/main/db-migrations"))
    }
  }
}

val compileKotlin by tasks.getting {
  dependsOn("generateMainFlagpoleDatabaseMigrations")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
  // These are required by the CI build:
  exclude("module-info.class")
  mergeServiceFiles()
  archiveClassifier.set(null as String?)
  isZip64 = true
  manifest {
    attributes(mapOf("Main-Class" to applicationMainClass))
  }
}

tasks {
  runShadow {
    environment(
      mapOf(
        "ENVIRONMENT" to "development",
        "SERVICE_NAME" to "flagpole",
        "REGION" to "us-east-1",
        "ACCOUNT_ID" to ""
      )
    )
  }
}
