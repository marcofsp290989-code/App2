plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.adaptivepower.engine"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.adaptivepower.engine"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
}
