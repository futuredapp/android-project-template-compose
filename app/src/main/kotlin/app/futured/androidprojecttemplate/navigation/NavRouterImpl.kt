package app.futured.androidprojecttemplate.navigation

import androidx.navigation.NavController
import app.futured.androidprojecttemplate.tools.extensions.subscribeForResult
import timber.log.Timber

/**
 * Class that triggers navigation actions on provided [navController].
 */
class NavRouterImpl(private val navController: NavController) : NavRouter {
    override fun popBackStack() {
        navController.navigateUp()
    }

    override fun navigateBack(popUpToDestination: Destination, inclusive: Boolean) {
        navController.popBackStack(route = popUpToDestination.route, inclusive = inclusive)
    }

    override fun navigateToDetail(title: String, subtitle: String?, value: String?) =
        Destination.Detail.buildRoute(title, subtitle, value).execute()

    override fun navigateToInfo() = Destination.Info.buildRoute().execute()

    override fun <T> navigateBackWithResult(key: String, value: T) {
        navController.previousBackStackEntry?.savedStateHandle?.also {
            it[key] = value
            navController.popBackStack()
        }
    }

    override fun <T> setCurrentResult(key: String, value: T) {
        navController.currentBackStackEntry?.savedStateHandle?.also {
            it[key] = value
        }
    }

    override fun <T> subscribeForResult(key: String, callback: (T) -> Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.subscribeForResult<T>(key) { callback(it) }
    }

    private fun String.execute(
        popUpToDestinationRoute: String? = null,
        isInclusive: Boolean = true,
    ) {
        Timber.d("## Navigate to $this, popupTo $popUpToDestinationRoute, inclusive $isInclusive")
        if (popUpToDestinationRoute != null) {
            navController.navigate(this) {
                popUpTo(popUpToDestinationRoute) {
                    inclusive = isInclusive
                }
            }
        } else {
            navController.navigate(this)
        }
    }
}
