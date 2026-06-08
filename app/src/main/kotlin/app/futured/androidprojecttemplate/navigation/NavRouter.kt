package app.futured.androidprojecttemplate.navigation

import app.futured.androidprojecttemplate.ui.screens.detail.DetailScreenArgs

interface NavRouter {
    fun popBackStack()
    fun navigateBack(popUpToDestination: MainRoute, inclusive: Boolean = false)

    fun navigateToHome()

    fun navigateToDetail(args: DetailScreenArgs)

    fun navigateToLogin()

    fun <T : Any> navigateBackWithResult(key: String, value: T)
    fun <T : Any> setCurrentResult(key: String, value: T)
}
