import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import hotwire_kt.HotwireKtDependencies
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jetbrains.kotlin.kapt")
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
  implementation(project(":ui"))
  implementation(project(":kotlinx-html-templates"))

  implementation(HotwireKtDependencies.mustacheCompiler)
  implementation(HotwireKtDependencies.kotlinxHtml)

  implementation(HotwireKtDependencies.okio)
  implementation(HotwireKtDependencies.moshiCore)
  implementation(HotwireKtDependencies.moshiKotlin)
  implementation(HotwireKtDependencies.wispConfig)
  implementation(HotwireKtDependencies.wispLogging)

  implementation(platform(HotwireKtDependencies.armeriaBom))
  implementation("com.linecorp.armeria:armeria-grpc")

  testImplementation(HotwireKtDependencies.kotestJunitRunnerJvm)
  testImplementation(HotwireKtDependencies.mockk)
}

java {
  sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
  compileJava {
    val compilerArgs = options.compilerArgs
    compilerArgs.add("-parameters")
  }

  compileKotlin {
    kotlinOptions {
      jvmTarget = "11"
      javaParameters = true
    }
  }
  compileTestKotlin {
    kotlinOptions {
      jvmTarget = "11"
      javaParameters = true
    }
  }

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

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
  environment("ENVIRONMENT", "test")
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
