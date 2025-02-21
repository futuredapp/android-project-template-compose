plugins {
    // Wondering why not `alias`? https://github.com/gradle/gradle/issues/17968
    // (this is only for plugins we already have dependency on in `buildSrc`)
    id(libs.plugins.conventions.lint.get().pluginId)
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
//     TODO enable after providing google-services.json
//    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.distribution)
}

android {
    compileSdk = ProjectSettings.compileSdkVersion
    namespace = ProjectSettings.applicationId

    defaultConfig {
        applicationId = ProjectSettings.applicationId
        minSdk = ProjectSettings.minSdk
        targetSdk = ProjectSettings.targetSdk
        versionCode = ProjectSettings.versionCode
        versionName = ProjectSettings.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments.apply {
                    put("room.schemaLocation", "$projectDir/schemas")
                }
            }
        }
    }

    packaging {
        resources.excludes += setOf(
            "META-INF/DEPENDENCIES",
            "META-INF/LICENSE.md",
            "META-INF/LICENSE-notice.md",
            "README.md",
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    sourceSets {
        getByName("main").java.setSrcDirs(setOf("src/main/kotlin"))
        create(ProjectSettings.Flavor.DEV).java.setSrcDirs(setOf("src/dev/kotlin"))
        create(ProjectSettings.Flavor.PROD).java.setSrcDirs(setOf("src/prod/kotlin"))
        create(ProjectSettings.Flavor.MOCK).java.setSrcDirs(setOf("src/mock/kotlin"))
        getByName("test").java.setSrcDirs(setOf("src/test/kotlin"))
        getByName("androidTest").java.setSrcDirs(setOf("src/androidTest/kotlin"))
    }

    signingConfigs {
        getByName(ProjectSettings.BuildType.DEBUG) {
            storeFile = rootProject.file("./keystore/debug.jks")
            storePassword = ProjectSettings.Debug.STORE_PASSWORD
            keyAlias = ProjectSettings.Debug.KEY_ALIAS
            keyPassword = ProjectSettings.Debug.KEY_PASSWORD
        }
        create(ProjectSettings.BuildType.RELEASE) {
            storeFile = rootProject.file("./keystore/release.jks")
            storePassword = ProjectSettings.Release.STORE_PASSWORD
            keyAlias = ProjectSettings.Release.KEY_ALIAS
            keyPassword = ProjectSettings.Release.KEY_PASSWORD
        }
    }

    buildTypes {
        buildTypes {
            getByName(ProjectSettings.BuildType.DEBUG) {
                isMinifyEnabled = false
                isShrinkResources = false
                signingConfig = signingConfigs.getByName(ProjectSettings.BuildType.DEBUG)
            }
            create(ProjectSettings.BuildType.ENTERPRISE) {
                isDebuggable = true
                isMinifyEnabled = true
                isShrinkResources = true
                signingConfig = signingConfigs.getByName(ProjectSettings.BuildType.DEBUG)
                proguardFile(getDefaultProguardFile("proguard-android.txt"))
                proguardFile(file("proguard-rules.pro"))
            }
            getByName(ProjectSettings.BuildType.RELEASE) {
                isMinifyEnabled = true
                isShrinkResources = true
                signingConfig = signingConfigs.getByName(ProjectSettings.BuildType.RELEASE)
                proguardFile(getDefaultProguardFile("proguard-android.txt"))
                proguardFile(file("proguard-rules.pro"))
            }
        }
    }

    flavorDimensions.add(ProjectSettings.Flavor.DIMENSION)

    productFlavors {
        create(ProjectSettings.Flavor.MOCK) {
            dimension = ProjectSettings.Flavor.DIMENSION
        }
        create(ProjectSettings.Flavor.DEV) {
            dimension = ProjectSettings.Flavor.DIMENSION
        }
        create(ProjectSettings.Flavor.PROD) {
            dimension = ProjectSettings.Flavor.DIMENSION
        }
    }

    lint {
        textReport = true // Write a text report to the console (Useful for CI logs)
        xmlReport = true // Write XML report
        abortOnError = false // Do not abort build when error is found -> Danger will report this to the MR
        explainIssues = false // HTML/XML reports are too verbose in console logs
        checkDependencies = false // Required to get all unused resource from other modules (disabled to speed up linting)
        checkTestSources = true // Also check test case code for lint issues
        checkReleaseBuilds = false // If we run a full lint analysis as build part in CI, we can skip redundant checks
    }
}

kotlin {
    jvmToolchain(JavaVersion.VERSION_17.majorVersion.toInt())

    compilerOptions {
        optIn.add("kotlin.RequiresOptIn")
    }
}

dependencies {

    // Support
    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)
    implementation(libs.activity.ktx)

    coreLibraryDesugaring(libs.core.jdk.desugaring)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.splashscreen)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.runtime)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // MVVM
    implementation(libs.arkitekt.usecases)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // NavigationComponents
    implementation(libs.navigation)
    implementation(libs.navigation.hilt)

    // Networking
    implementation(libs.okHttp)
    implementation(libs.logging)
    implementation(libs.retrofit)
    implementation(libs.coil)
    implementation(libs.coil.network)

    // Serialization
    implementation(libs.serialization.json)
    implementation(libs.serialization.converter)

    // Other
    implementation(libs.timber)

    // Testing
    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.mockk)
    testImplementation(libs.test.junit)

    // Lint
    lintChecks(libs.compose.lint)
}
