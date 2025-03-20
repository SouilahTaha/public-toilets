plugins {
  id("kotlin-kapt")
  alias(libs.plugins.hilt)
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.compose)
  alias(libs.plugins.ksp)
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.taha.publictoilets"
  compileSdk = 35

  buildFeatures {
    buildConfig = true
  }
  defaultConfig {
    applicationId = "com.taha.publictoilets"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    val apiKey = project.findProperty("MAPS_API_KEY") as String? ?: ""
    resValue("string", "maps_api_key", apiKey)
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
}

dependencies {

  api(project(":domain"))
  api(project(":data"))
  api(project(":design-system"))
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.hilt.android)
  implementation(libs.play.services.location)
  ksp(libs.hilt.compiler)
  implementation(libs.navigation.compose)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.material.icons.extended)
  implementation(libs.androidx.ui.tooling.preview.android)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.play.services.maps)
  implementation(libs.maps.compose)
  testImplementation(libs.junit)
  testImplementation(libs.mockk)
  testImplementation(libs.kotlinx.coroutines.test)

  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}