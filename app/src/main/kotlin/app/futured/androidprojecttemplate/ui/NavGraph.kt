@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
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
import androidx.navigation3.runtime.metadata
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import app.futured.androidprojecttemplate.navigation.BottomSheetSceneStrategy
import app.futured.androidprojecttemplate.navigation.MainRoute
import app.futured.androidprojecttemplate.navigation.NavRouterImpl
import app.futured.androidprojecttemplate.navigation.ResultStore
import app.futured.androidprojecttemplate.navigation.rememberResultStore
import app.futured.androidprojecttemplate.ui.components.BottomNavTab
import app.futured.androidprojecttemplate.ui.components.BottomNavigationBar
import app.futured.androidprojecttemplate.ui.screens.first.FirstScreen
import app.futured.androidprojecttemplate.ui.screens.login.LoginScreen
import app.futured.androidprojecttemplate.ui.screens.picker.PickerScreen
import app.futured.androidprojecttemplate.ui.screens.profile.ProfileScreen
import app.futured.androidprojecttemplate.ui.screens.second.SecondScreen
import app.futured.androidprojecttemplate.ui.screens.third.ThirdScreen
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
            onEvent<NavigateToFirstEvent> {
                backStackNavigator.navigateToFirst()
            }
        }
    }

    val currentRoute = backStack.lastOrNull()
    val showBottomBar = currentRoute is MainRoute.First || currentRoute is MainRoute.Profile

    CompositionLocalProvider(LocalResultStore provides resultStore) {
        Scaffold(
            modifier = modifier,
            contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.systemBars),
            bottomBar = {
                if (showBottomBar) {
                    BottomNavigationBar(
                        currentRoute = currentRoute,
                        onTabSelected = { tab ->
                            when (tab) {
                                BottomNavTab.Home -> backStackNavigator.selectHomeTab()
                                BottomNavTab.Profile -> backStackNavigator.selectProfileTab()
                            }
                        },
                    )
                }
            },
        ) { contentPadding ->
            NavDisplay(
                modifier = Modifier.padding(contentPadding),
                backStack = backStack,
                onBack = dropUnlessResumed { backStackNavigator.popBackStack() },
                transitionSpec = { forwardTransition() },
                popTransitionSpec = { popTransition() },
                predictivePopTransitionSpec = { predictivePopTransition() },
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                ),
                sceneStrategies = listOf(bottomSheetStrategy),
                entryProvider = entryProvider {
                    entry<MainRoute.Login> {
                        LoginScreen(navigation = backStackNavigator)
                    }
                    entry<MainRoute.First>(
                        metadata = metadata {
                            put(NavDisplay.TransitionKey) { fadeIn() togetherWith fadeOut() }
                            put(NavDisplay.PopTransitionKey) { fadeIn() togetherWith fadeOut() }
                        },
                    ) {
                        FirstScreen(navigation = backStackNavigator)
                    }
                    entry<MainRoute.Second> {
                        SecondScreen(navigation = backStackNavigator)
                    }
                    entry<MainRoute.Third> {
                        ThirdScreen(it.args, navigation = backStackNavigator)
                    }
                    entry<MainRoute.Profile>(
                        metadata = metadata {
                            put(NavDisplay.TransitionKey) { fadeIn() togetherWith fadeOut() }
                            put(NavDisplay.PopTransitionKey) { fadeIn() togetherWith fadeOut() }
                        },
                    ) {
                        ProfileScreen(navigation = backStackNavigator)
                    }
                    entry<MainRoute.Picker>(metadata = BottomSheetSceneStrategy.bottomSheet()) {
                        PickerScreen(it.args, navigation = backStackNavigator)
                    }
                },
            )
        }
    }
}

val LocalResultStore = compositionLocalOf<ResultStore?> {
    null
}

/**
 * Incoming screen slides in from the right edge while fading in
 * outgoing screen slides one-third to the left while fading out.
 */
private fun forwardTransition(): ContentTransform =
    slideInHorizontally(initialOffsetX = { it }) + fadeIn() togetherWith
        slideOutHorizontally(targetOffsetX = { -it / 3 }) + fadeOut()

/**
 * Reverse of the forward transition: incoming screen slides in from one-third left while fading in
 * outgoing slides off to the right while fading out.
 */
private fun popTransition(): ContentTransform =
    slideInHorizontally(initialOffsetX = { -it / 3 }) + fadeIn() togetherWith
        slideOutHorizontally(targetOffsetX = { it }) + fadeOut()

/**
 * Same motion as popTransition, used while the user is driving the back gesture (predictive back).
 */
private fun predictivePopTransition(): ContentTransform =
    slideInHorizontally(initialOffsetX = { -it / 3 }) + fadeIn() togetherWith
        slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
