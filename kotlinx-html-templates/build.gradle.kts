import hotwire_kt.HotwireKtDependencies
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jetbrains.kotlin.kapt")
  id("org.jetbrains.kotlin.plugin.allopen")
}

dependencies {
  implementation(HotwireKtDependencies.kotlinxHtml)
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
}

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
  environment("ENVIRONMENT", "test")
}


