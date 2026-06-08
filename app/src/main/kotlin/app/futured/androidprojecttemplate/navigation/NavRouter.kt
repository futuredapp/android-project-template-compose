package app.futured.androidprojecttemplate.navigation

import app.futured.androidprojecttemplate.ui.screens.picker.PickerScreenArgs
import app.futured.androidprojecttemplate.ui.screens.third.ThirdScreenArgs

interface NavRouter {
    fun popBackStack()
    fun navigateBack(popUpToDestination: MainRoute, inclusive: Boolean = false)

    fun navigateToFirst()

    fun navigateToSecond()

    fun navigateToThird(args: ThirdScreenArgs)

    fun navigateToPicker(args: PickerScreenArgs)

    fun navigateToLogin()

    fun selectHomeTab()

    fun selectProfileTab()

    fun <T : Any> navigateBackWithResult(key: String, value: T)
    fun <T : Any> setCurrentResult(key: String, value: T)
}
