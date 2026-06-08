package app.futured.androidprojecttemplate.navigation

import androidx.navigation3.runtime.NavKey
import app.futured.androidprojecttemplate.ui.screens.picker.PickerScreenArgs
import app.futured.androidprojecttemplate.ui.screens.third.ThirdScreenArgs
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoute : NavKey {
    @Serializable
    data object Login : MainRoute

    @Serializable
    data object First : MainRoute

    @Serializable
    data object Second : MainRoute

    @Serializable
    data class Third(val args: ThirdScreenArgs) : MainRoute

    @Serializable
    data object Profile : MainRoute

    @Serializable
    data class Picker(val args: PickerScreenArgs) : MainRoute
}
