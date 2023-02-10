import hotwire_kt.Dependencies

plugins {
  id("app.cash.sqldelight")
  id("com.squareup.wire")
  application
  id("com.github.johnrengelman.shadow")
}

val applicationMainClass = "xyz.adrw.flagpole.FlagpoleServiceKt"
application {
  mainClass.set(applicationMainClass)
}

dependencies {
  implementation(projects.miskDb.miskDbFeature)

  implementation(Dependencies.logbackClassic)
  implementation(Dependencies.misk)
  implementation(Dependencies.miskAdmin)
  implementation(Dependencies.miskCore)
  implementation(Dependencies.miskFeature)
  implementation(Dependencies.miskInject)
  implementation(Dependencies.wispConfig)
  implementation(Dependencies.wispLogging)

  // Database
  implementation(Dependencies.miskJdbc)
  implementation(Dependencies.sqldelightJdbcDriver)
}

wire {
  protoLibrary = true

  sourcePath("src/main/proto")

  kotlin {
    includes = listOf(
      "xyz.adrw.protos.flagpole.FlagpoleApiService",
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
      dialect(Dependencies.sqldelightMysqlDialect)
      packageName.set("xyz.adrw.flagpole.db")
      sourceFolders.set(listOf("sqldelight", "resources/db-migrations"))
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
    environment(mapOf(
      "ENVIRONMENT" to "development",
      "SERVICE_NAME" to "flagpole",
      "REGION" to "us-east-1",
      "ACCOUNT_ID" to ""
    ))
  }
}
