package app.futured.androidprojecttemplate.data.remote.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LoggingFormat
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoggingPlugin @Inject constructor() : HttpClientPlugin {

    companion object {
        private val LOG_LEVEL = LogLevel.ALL
    }

    override fun install(config: HttpClientConfig<*>) {
        config.install(Logging) {
            logger = TimberLogger()
            level = LOG_LEVEL
            format = LoggingFormat.OkHttp
        }
    }

    private class TimberLogger : Logger {
        override fun log(message: String) {
            Timber.tag("Ktor").d(message)
        }
    }
}
