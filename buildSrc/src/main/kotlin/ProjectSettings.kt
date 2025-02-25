object ProjectSettings {
    const val applicationId = "app.futured.androidprojecttemplate"
    const val compileSdkVersion = 35
    const val targetSdk = 35
    const val minSdk = 29
    val versionName = System.getenv("ANDROID_VERSION_NAME") ?: "1.0.0"
    val versionCode = System.getenv("ANDROID_BUILD_NUMBER")?.toIntOrNull() ?: 1

    object Gradle {
        const val TaskGroup = "futured"
    }

    object Flavor {
        const val DIMENSION = "api"

        const val MOCK = "mock"
        const val DEV = "dev"
        const val PROD = "prod"
    }

    object BuildType {
        const val DEBUG = "debug"
        const val ENTERPRISE = "enterprise"
        const val RELEASE = "release"
    }

    object Debug {
        const val KEY_ALIAS = "androiddebugkey"
        const val KEY_PASSWORD = "android"
        const val STORE_PASSWORD = "android"
    }

    object Release {
        val KEY_ALIAS = System.getenv("ANDROID_KEY_ALIAS") ?: ""
        val KEY_PASSWORD = System.getenv("ANDROID_KEY_PASSWORD") ?: ""
        val STORE_PASSWORD = System.getenv("ANDROID_KEYSTORE_PASSWORD") ?: ""
    }

    const val TASK_GROUP = "futured"
}
