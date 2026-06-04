package app.futured.androidprojecttemplate.navigation

interface NavRouter {
    fun popBackStack()
    fun navigateBack(popUpToDestination: MainRoute, inclusive: Boolean = false)

    fun navigateToHome()

    fun navigateToDetail(title: String, subtitle: String? = null, value: String? = null)

    fun <T : Any> navigateBackWithResult(key: String, value: T)
    fun <T : Any> setCurrentResult(key: String, value: T)
}
