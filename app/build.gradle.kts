
plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 33
    namespace = "com.adaptive.power"

    defaultConfig {
        applicationId = "com.adaptive.power"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "0.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("org.tensorflow:tensorflow-lite:2.11.0") // optional tflite if later used
}
