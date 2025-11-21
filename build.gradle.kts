// Top-level build file where you can add configuration options common to all sub-projects/modules.

// 1. O bloco 'pluginManagement' substitui o 'buildscript' para definir onde 
//    os plugins (como o Android Application Plugin) podem ser encontrados.
pluginManagement {
    repositories {
        // O repositório central do Gradle para plugins
        gradlePluginPortal()
        // Repositório do Google, essencial para o Android Gradle Plugin (AGP)
        google() 
        // Repositório Maven Central
        mavenCentral()
    }
}

// 2. Os plugins são definidos aqui. As versões 8.2.0 e 1.9.20 parecem corretas.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}

// 3. O bloco 'allprojects' ainda é usado para definir repositórios para 
//    dependências (bibliotecas, não plugins). Manter 'google()' e 'mavenCentral()' aqui.
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Tarefa clean (mantida sem alterações)
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

// NOTA: O bloco 'dependencies' que estava em 'buildscript' já não é necessário aqui,
// pois o Gradle obtém a dependência do AGP através do sistema de plugins.
