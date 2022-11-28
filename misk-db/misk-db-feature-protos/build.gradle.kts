plugins {
  `java-library`
}

java.sourceSets["main"].resources {
  srcDirs("src/main/proto")
}
