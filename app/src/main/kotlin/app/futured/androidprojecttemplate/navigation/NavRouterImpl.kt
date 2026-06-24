package app.futured.androidprojecttemplate.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import app.futured.androidprojecttemplate.ui.screens.picker.PickerScreenArgs
import app.futured.androidprojecttemplate.ui.screens.third.ThirdScreenArgs

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

    override fun navigateToFirst() {
        backStack.clear()
        backStack.add(MainRoute.First)
    }

    override fun navigateToSecond() {
        backStack.add(MainRoute.Second)
    }

    override fun navigateToThird(args: ThirdScreenArgs) {
        backStack.add(MainRoute.Third(args))
    }

    override fun navigateToPicker(args: PickerScreenArgs) {
        backStack.add(MainRoute.Picker(args))
    }

    override fun navigateToLogin() {
        backStack.clear()
        backStack.add(MainRoute.Login)
    }

    override fun selectHomeTab() {
        while (backStack.isNotEmpty() && backStack.last() != MainRoute.First) {
            backStack.removeLastOrNull()
        }
    }

    override fun selectProfileTab() {
        if (backStack.lastOrNull() != MainRoute.Profile) {
            backStack.add(MainRoute.Profile)
        }
    }

    override fun <T : Any> navigateBackWithResult(key: String, value: T) {
        resultStore.put(key, value)
        backStack.removeLastOrNull()
    }

    override fun <T : Any> setCurrentResult(key: String, value: T) {
        resultStore.put(key, value)
    }
}
