package app.futured.androidprojecttemplate.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface MainRoute : NavKey {
    @Serializable
    data object Login : MainRoute

    @Serializable
    data object Home : MainRoute

    @Serializable
    data class Detail(val title: String, val subtitle: String? = null, val value: String? = null) : MainRoute
}
