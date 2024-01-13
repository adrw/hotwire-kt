import org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

buildscript {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    // Misk
    maven(url = "https://s01.oss.sonatype.org/content/repositories/releases/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/comsquareup-1061/")
    // SqlDelight
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
  }

  dependencies {
    classpath(libs.kotlinAllOpenPlugin)
    classpath(libs.kotlinGradlePlugin)
    classpath(libs.junitGradlePlugin)
    classpath(libs.shadowJarPlugin)
    classpath(libs.sqldelightGradle)
    classpath(libs.wireGradlePlugin)
  }
}

// Run gradle buildHealth to get dependency report for unused and duplicates
plugins {
  id("com.autonomousapps.dependency-analysis") version "1.2.1"
}

subprojects {
  buildscript {
    repositories {
      mavenCentral()
      maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
      jcenter()
      // Misk
      maven(url = "https://s01.oss.sonatype.org/content/repositories/releases/")
      maven(url = "https://s01.oss.sonatype.org/content/repositories/comsquareup-1061/")
      // SqlDelight
      maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
  }

  apply(plugin = "java")
  apply(plugin = "kotlin")

  repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
    jcenter()
    // Misk
    maven(url = "https://s01.oss.sonatype.org/content/repositories/releases/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/comsquareup-1061/")
    // SqlDelight
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
  }

  tasks {
    withType<JavaCompile> {
      val compilerArgs = options.compilerArgs
      compilerArgs.add("-parameters")
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
      kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        javaParameters = true
      }
    }

    withType<Test> {
      useJUnitPlatform()
      testLogging {
        events(STARTED, PASSED, SKIPPED, FAILED)
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showStackTraces = true
      }
      environment("ENVIRONMENT", "test")
    }
  }
}

// Disable the Gradle wrapper if Gradle is managed by Hermit
tasks.named<Wrapper>("wrapper") {
  enabled = false
}
