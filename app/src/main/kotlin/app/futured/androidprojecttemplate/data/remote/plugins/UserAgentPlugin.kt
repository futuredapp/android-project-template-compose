package app.futured.androidprojecttemplate.data.remote.plugins

import android.content.Context
import android.os.Build
import app.futured.androidprojecttemplate.BuildConfig
import app.futured.androidprojecttemplate.R
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.UserAgent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAgentPlugin @Inject constructor(@param:ApplicationContext private val context: Context) : HttpClientPlugin {

    private val userAgentString = listOf(
        "${context.getString(R.string.app_name)}/${BuildConfig.VERSION_NAME}",
        "(${BuildConfig.APPLICATION_ID}; build:${BuildConfig.VERSION_CODE}; Android ${Build.VERSION.RELEASE}; Model:${Build.MANUFACTURER} ${Build.MODEL})",
        "ktor-client/${BuildConfig.KTOR_VERSION}",
    ).joinToString(separator = " ")

    override fun install(config: HttpClientConfig<*>) {
        config.install(UserAgent) {
            agent = userAgentString
        }
    }
}
