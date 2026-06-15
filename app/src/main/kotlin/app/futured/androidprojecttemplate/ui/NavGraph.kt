package app.futured.androidprojecttemplate.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import app.futured.androidprojecttemplate.navigation.BottomSheetSceneStrategy
import app.futured.androidprojecttemplate.navigation.MainRoute
import app.futured.androidprojecttemplate.navigation.NavRouterImpl
import app.futured.androidprojecttemplate.navigation.ResultStore
import app.futured.androidprojecttemplate.navigation.rememberResultStore
import app.futured.androidprojecttemplate.ui.screens.detail.DetailScreen
import app.futured.androidprojecttemplate.ui.screens.home.HomeScreen
import app.futured.androidprojecttemplate.ui.screens.login.LoginScreen
import app.futured.arkitekt.compose.EventsEffect
import app.futured.arkitekt.compose.onEvent

@Composable
fun NavGraph(modifier: Modifier = Modifier, appViewModel: AppViewModel = hiltViewModel()) {
    val backStack = rememberNavBackStack(MainRoute.Login)
    val bottomSheetStrategy = remember { BottomSheetSceneStrategy<NavKey>() }
    val resultStore = rememberResultStore()
    val backStackNavigator = remember(resultStore) {
        NavRouterImpl(backStack, resultStore)
    }

    with(appViewModel) {
        EventsEffect {
            onEvent<NavigateToHomeEvent> {
                backStackNavigator.navigateToHome()
            }
        }
    }

    CompositionLocalProvider(LocalResultStore provides resultStore) {
        NavDisplay(
            modifier = modifier,
            backStack = backStack,
            onBack = dropUnlessResumed { backStackNavigator.popBackStack() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            sceneStrategies = listOf(bottomSheetStrategy),
            entryProvider = entryProvider {
                entry<MainRoute.Login> {
                    LoginScreen(navigation = backStackNavigator)
                }
                entry<MainRoute.Home> {
                    HomeScreen(navigation = backStackNavigator)
                }
                entry<MainRoute.Detail> {
                    DetailScreen(it.args, navigation = backStackNavigator)
                }
            },
        )
    }
}

val LocalResultStore = compositionLocalOf<ResultStore?> {
    null
}
