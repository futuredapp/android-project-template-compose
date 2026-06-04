package app.futured.androidprojecttemplate.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * Class that triggers navigation actions on the provided [backStack].
 */
class NavRouterImpl(private val backStack: NavBackStack<NavKey>, private val resultStore: ResultStore) : NavRouter {

    override fun popBackStack() {
        backStack.removeLastOrNull()
    }

    override fun navigateBack(popUpToDestination: MainRoute, inclusive: Boolean) {
        backStack.removeLastOrNull()
    }

    override fun navigateToHome() {
        backStack.add(MainRoute.Home)
    }

    override fun navigateToDetail(title: String, subtitle: String?, value: String?) {
        backStack.add(MainRoute.Detail(title = title, subtitle = subtitle, value = value))
    }

    override fun <T : Any> navigateBackWithResult(key: String, value: T) {
        resultStore.put(key, value)
        backStack.removeLastOrNull()
    }

    override fun <T : Any> setCurrentResult(key: String, value: T) {
        resultStore.put(key, value)
    }
}
