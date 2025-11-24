
plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.adaptive.power"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.adaptive.power"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {}
