pluginManagement {
    repositories {
        gradlePluginPortal()
        google() 
        mavenCentral()
    }
}
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}
// ... restante código
