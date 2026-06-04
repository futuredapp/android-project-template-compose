package app.futured.androidprojecttemplate.data.remote.plugins

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentNegotiationPlugin @Inject constructor(private val json: Json) : HttpClientPlugin {

    override fun install(config: HttpClientConfig<*>) {
        config.install(ContentNegotiation) {
            json(json)
        }

        config.install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}
