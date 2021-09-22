buildscript {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }

  dependencies {
    classpath(hotwire_kt.HotwireKtDependencies.kotlinAllOpenPlugin)
    classpath(hotwire_kt.HotwireKtDependencies.kotlinGradlePlugin)
    classpath(hotwire_kt.HotwireKtDependencies.junitGradlePlugin)
    classpath(hotwire_kt.HotwireKtDependencies.shadowJarPlugin)
  }
}

subprojects {
  buildscript {
    repositories {
      mavenCentral()
      maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
      jcenter()
    }
  }

  repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
    jcenter()
  }
}

// Disable the Gradle wrapper if Gradle is managed by Hermit
tasks.named<Wrapper>("wrapper") {
  enabled = true
}
