package app.futured.androidprojecttemplate.data.remote

import de.jensklingenberg.ktorfit.http.GET
import java.time.ZonedDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

interface ApiService {

    @GET("api/user/2")
    suspend fun user(): SampleApiModel

    @Serializable
    data class SampleApiModel(val id: String, @Contextual val dateTime: ZonedDateTime)
}
