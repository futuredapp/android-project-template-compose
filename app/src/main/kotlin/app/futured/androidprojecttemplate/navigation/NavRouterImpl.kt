package app.futured.androidprojecttemplate.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import app.futured.androidprojecttemplate.ui.screens.detail.DetailScreenArgs

/**
 * Class that triggers navigation actions on the provided [backStack].
 */
class NavRouterImpl(private val backStack: NavBackStack<NavKey>, private val resultStore: ResultStore) : NavRouter {

    override fun popBackStack() {
        backStack.removeLastOrNull()
    }

    override fun navigateBack(popUpToDestination: MainRoute, inclusive: Boolean) {
        while (backStack.isNotEmpty() && backStack.last() != popUpToDestination) {
            backStack.removeLastOrNull()
        }
        if (inclusive) {
            backStack.removeLastOrNull()
        }
    }

    override fun navigateToHome() {
        backStack.add(MainRoute.Home)
    }

    override fun navigateToDetail(args: DetailScreenArgs) {
        backStack.add(MainRoute.Detail(args))
    }

    override fun navigateToLogin() {
        backStack.clear()
        backStack.add(MainRoute.Login)
    }

    override fun <T : Any> navigateBackWithResult(key: String, value: T) {
        resultStore.put(key, value)
        backStack.removeLastOrNull()
    }

    override fun <T : Any> setCurrentResult(key: String, value: T) {
        resultStore.put(key, value)
    }
}
