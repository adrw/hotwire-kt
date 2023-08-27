import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import hotwire_kt.Dependencies

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("com.github.johnrengelman.shadow")
  id("org.jetbrains.kotlin.plugin.allopen")
  application
}

val applicationMainClass = "xyz.adrw.hotwire.ApplicationKt"

application {
  mainClass.set(applicationMainClass)
  mainClassName = applicationMainClass // for shadow jar
}

dependencies {
  implementation(projects.hotwireKt.kotlinxHtmlTemplates)
  implementation(projects.hotwireKt.tailwindsUi)

  implementation(Dependencies.kotlinxHtml)
  implementation(Dependencies.okio)
  implementation(Dependencies.moshiCore)
  implementation(Dependencies.moshiKotlin)
  implementation(Dependencies.wispLogging)

  implementation(platform(Dependencies.armeriaBom))
  implementation("com.linecorp.armeria:armeria-grpc")
}

tasks {
  runShadow {
    val applicationArgs = mutableListOf<String>()

    if (project.hasProperty("args")) {
      val cmdLineArgs = project.properties["args"] as String
      cmdLineArgs.split(" ").forEach {
        applicationArgs.add(it)
      }
    }

    args = applicationArgs
  }
}

tasks.withType<ShadowJar> {
  // These are required by the CI build:
  exclude("module-info.class")
  mergeServiceFiles()
  setZip64(true)
  archiveClassifier.set(null as String?)
  manifest {
    attributes(mapOf("Main-Class" to applicationMainClass))
  }
}
