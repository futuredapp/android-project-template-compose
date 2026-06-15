package app.futured.androidprojecttemplate.navigation

import androidx.navigation3.runtime.NavKey
import app.futured.androidprojecttemplate.ui.screens.detail.DetailScreenArgs
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoute : NavKey {
    @Serializable
    data object Login : MainRoute

    @Serializable
    data object Home : MainRoute

    @Serializable
    data class Detail(val args: DetailScreenArgs) : MainRoute
}
