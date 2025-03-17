plugins {
  id("java-library")
  alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
  compilerOptions {
    jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
  }
}
dependencies {
  api(project(":domain"))
  implementation(libs.javax.inject)

  implementation(libs.retrofit)
  implementation(libs.retrofit.converter.gson)
  testImplementation(libs.junit)
  testImplementation(libs.mockk)
  testImplementation(libs.kotlinx.coroutines.test)
}