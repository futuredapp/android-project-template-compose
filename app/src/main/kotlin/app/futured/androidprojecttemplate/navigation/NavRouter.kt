package app.futured.androidprojecttemplate.navigation

interface NavRouter {
    fun popBackStack()
    fun navigateBack(popUpToDestination: Destination, inclusive: Boolean = false)

    fun navigateToDetail(title: String, subtitle: String? = null, value: String? = null)
    fun navigateToInfo()

    fun <T> navigateBackWithResult(key: String, value: T)
    fun <T> setCurrentResult(key: String, value: T)
    fun <T> subscribeForResult(key: String, callback: (T) -> Unit)
}
