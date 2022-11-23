package hotwire_kt

object Dependencies {
  val armeriaBom = "com.linecorp.armeria:armeria-bom:1.11.0"
  val junitApi = "org.junit.jupiter:junit-jupiter-api:5.8.2"
  val junitEngine = "org.junit.jupiter:junit-jupiter-engine:5.8.2"
  val junitGradlePlugin = "org.junit.platform:junit-platform-gradle-plugin:1.2.0"
  val junitParams = "org.junit.jupiter:junit-jupiter-params:5.6.0"
  val kotestJunitRunnerJvm = "io.kotest:kotest-runner-junit5-jvm:4.6.2"
  val kotlinAllOpenPlugin = "org.jetbrains.kotlin:kotlin-allopen:${Versions.kotlin}"
  val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  val kotlinReflection = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
  val kotlinStdLibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
  val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
  val kotlinxHtml = "org.jetbrains.kotlinx:kotlinx-html-jvm:0.6.4"
  val logbackClassic = "ch.qos.logback:logback-classic:1.2.5"
  val misk = "com.squareup.misk:misk:${Versions.misk}"
  val miskActions = "com.squareup.misk:misk-actions:${Versions.misk}"
  val miskAdmin = "com.squareup.misk:misk-admin:${Versions.misk}"
  val miskAws = "com.squareup.misk:misk-aws:${Versions.misk}"
  val miskAws2Dynamodb = "com.squareup.misk:misk-aws2-dynamodb:${Versions.misk}"
  val miskAwsDynamodb = "com.squareup.misk:misk-aws-dynamodb:${Versions.misk}"
  val miskCore = "com.squareup.misk:misk-core:${Versions.misk}"
  val miskCrypto = "com.squareup.misk:misk-crypto:${Versions.misk}"
  val miskDatadog = "com.squareup.misk:misk-datadog:${Versions.misk}"
  val miskFeature = "com.squareup.misk:misk-feature:${Versions.misk}"
  val miskFeatureTesting = "com.squareup.misk:misk-feature-testing:${Versions.misk}"
  val miskGrpcReflect = "com.squareup.misk:misk-grpc-reflect:${Versions.misk}"
  val miskHibernate = "com.squareup.misk:misk-hibernate:${Versions.misk}"
  val miskHibernateTesting = "com.squareup.misk:misk-hibernate-testing:${Versions.misk}"
  val miskInject = "com.squareup.misk:misk-inject:${Versions.misk}"
  val miskJdbc = "com.squareup.misk:misk-jdbc:${Versions.misk}"
  val miskJdbcTesting = "com.squareup.misk:misk-jdbc-testing:${Versions.misk}"
  val miskJobqueue = "com.squareup.misk:misk-jobqueue:${Versions.misk}"
  val miskJobqueueTesting = "com.squareup.misk:misk-jobqueue-testing:${Versions.misk}"
  val miskLaunchdarkly = "com.squareup.misk:misk-launchdarkly:${Versions.misk}"
  val miskMetrics = "com.squareup.misk:misk-metrics:${Versions.misk}"
  val miskPrometheus = "com.squareup.misk:misk-prometheus:${Versions.misk}"
  val miskService = "com.squareup.misk:misk-service:${Versions.misk}"
  val miskSlack = "com.squareup.misk:misk-slack:${Versions.misk}"
  val miskTesting = "com.squareup.misk:misk-testing:${Versions.misk}"
  val miskTransactionalJobqueue = "com.squareup.misk:misk-transactional-jobqueue:${Versions.misk}"
  val miskZookeeper = "com.squareup.misk:misk-zookeeper:${Versions.misk}"
  val miskZookeeperTesting = "com.squareup.misk:misk-zookeeper-testing:${Versions.misk}"
  val mockk = "io.mockk:mockk:1.12.0"
  val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
  val moshiCore = "com.squareup.moshi:moshi:${Versions.moshi}"
  val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
  val mustacheCompiler = "com.github.spullara.mustache.java:compiler:0.9.5"
  val okio = "com.squareup.okio:okio:3.0.0"
  val shadowJarPlugin = "gradle.plugin.com.github.johnrengelman:shadow:7.1.2"
  val sqldelightDriverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"
  val sqldelightDriverJvm = "com.squareup.sqldelight:sqlite-driver:${Versions.sqldelight}"
  val sqldelightDriverNative = "com.squareup.sqldelight:native-driver:${Versions.sqldelight}"
  val sqldelightGradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqldelight}"
  val sqldelightJdbc = "org.xerial:sqlite-jdbc:3.34.0"
  val sqldelightJdbcDriver = "com.squareup.sqldelight:jdbc-driver:${Versions.sqldelight}"
  val wireCompiler = "com.squareup.wire:wire-compiler:${Versions.wire}"
  val wireGradlePlugin = "com.squareup.wire:wire-gradle-plugin:${Versions.wire}"
  val wireGrpcClient = "com.squareup.wire:wire-grpc-client:${Versions.wire}"
  val wireMoshiAdapter = "com.squareup.wire:wire-moshi-adapter:${Versions.wire}"
  val wireRuntime = "com.squareup.wire:wire-runtime:${Versions.wire}"
  val wireSchema = "com.squareup.wire:wire-schema:${Versions.wire}"
  val wispAwsEnvironment = "app.cash.wisp:wisp-aws-environment:${Versions.wisp}"
  val wispClient = "app.cash.wisp:wisp-client:${Versions.wisp}"
  val wispConfig = "app.cash.wisp:wisp-config:${Versions.wisp}"
  val wispContainersTesting = "app.cash.wisp:wisp-containers-testing:${Versions.wisp}"
  val wispDeployment = "app.cash.wisp:wisp-deployment:${Versions.wisp}"
  val wispDeploymentTesting = "app.cash.wisp:wisp-deployment-testing:${Versions.wisp}"
  val wispFeature = "app.cash.wisp:wisp-feature:${Versions.wisp}"
  val wispFeatureTesting = "app.cash.wisp:wisp-feature-testing:${Versions.wisp}"
  val wispLaunchDarkly = "app.cash.wisp:wisp-launchdarkly:${Versions.wisp}"
  val wispLease = "app.cash.wisp:wisp-lease:${Versions.wisp}"
  val wispLeaseTesting = "app.cash.wisp:wisp-lease-testing:${Versions.wisp}"
  val wispLogging = "app.cash.wisp:wisp-logging:${Versions.wisp}"
  val wispLoggingTesting = "app.cash.wisp:wisp-logging-testing:${Versions.wisp}"
  val wispMoshi = "app.cash.wisp:wisp-moshi:${Versions.wisp}"
  val wispResourceLoader = "app.cash.wisp:wisp-resource-loader:${Versions.wisp}"
  val wispResourceLoaderTesting = "app.cash.wisp:wisp-resource-loader-testing:${Versions.wisp}"
  val wispSsl = "app.cash.wisp:wisp-ssl:${Versions.wisp}"
  val wispTask = "app.cash.wisp:wisp-task:${Versions.wisp}"
  val wispTimeTesting = "app.cash.wisp:wisp-time-testing:${Versions.wisp}"
  val wispToken = "app.cash.wisp:wisp-token:${Versions.wisp}"
  val wispTokenTesting = "app.cash.wisp:wisp-token-testing:${Versions.wisp}"
  val wispTracing = "app.cash.wisp:wisp-tracing:${Versions.wisp}"
}