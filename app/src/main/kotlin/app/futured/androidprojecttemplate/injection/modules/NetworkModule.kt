package app.futured.androidprojecttemplate.injection.modules

import app.futured.androidprojecttemplate.data.remote.ApiService
import app.futured.androidprojecttemplate.data.remote.createApiService
import app.futured.androidprojecttemplate.data.remote.plugins.ContentNegotiationPlugin
import app.futured.androidprojecttemplate.data.remote.plugins.HttpTimeoutPlugin
import app.futured.androidprojecttemplate.data.remote.plugins.LoggingPlugin
import app.futured.androidprojecttemplate.data.remote.plugins.UserAgentPlugin
import app.futured.androidprojecttemplate.data.remote.result.NetworkResultConverterFactory
import app.futured.androidprojecttemplate.injection.qualifiers.ApiUrl
import app.futured.androidprojecttemplate.tools.Constants.Api.BASE_PROD_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @ApiUrl
    internal fun apiUrl(): String = BASE_PROD_URL

    @Provides
    @Singleton
    fun provideHttpClient(
        contentNegotiationPlugin: ContentNegotiationPlugin,
        httpTimeoutPlugin: HttpTimeoutPlugin,
        loggingPlugin: LoggingPlugin,
        userAgentPlugin: UserAgentPlugin,
    ): HttpClient = HttpClient(CIO) {
        contentNegotiationPlugin.install(this)
        httpTimeoutPlugin.install(this)
        loggingPlugin.install(this)
        userAgentPlugin.install(this)
    }

    @Provides
    @Singleton
    fun provideKtorfit(
        @ApiUrl apiUrl: String,
        client: HttpClient,
        networkResultConverterFactory: NetworkResultConverterFactory,
    ): Ktorfit = Ktorfit.Builder()
        .baseUrl(apiUrl)
        .httpClient(client)
        .converterFactories(networkResultConverterFactory)
        .build()

    @Provides
    @Singleton
    fun provideApiService(ktorfit: Ktorfit): ApiService = ktorfit.createApiService()
}
