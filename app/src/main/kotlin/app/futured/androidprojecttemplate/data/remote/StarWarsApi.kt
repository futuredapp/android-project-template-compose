package app.futured.androidprojecttemplate.data.remote

import app.futured.androidprojecttemplate.data.remote.result.NetworkResult
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface StarWarsApi {

    @GET("people/{id}")
    suspend fun getPerson(
        @Path("id") personId: Int,
    ): NetworkResult<Person>

    @Serializable
    data class Person(
        @SerialName("name") val name: String? = null,
        @SerialName("homeworld") val homeworld: String? = null,
        @SerialName("gender") val gender: String? = null,
        @SerialName("url") val url: String? = null,
    )
}
