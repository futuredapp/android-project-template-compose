pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.buildFileName = "build.gradle.kts"
include(":app")
includeBuild("convention-plugins")
