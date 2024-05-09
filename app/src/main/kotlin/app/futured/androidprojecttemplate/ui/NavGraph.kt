package app.futured.androidprojecttemplate.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.futured.androidprojecttemplate.navigation.Destination
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.navigation.NavRouterImpl
import app.futured.androidprojecttemplate.navigation.composableDialog
import app.futured.androidprojecttemplate.navigation.composableScreen
import app.futured.androidprojecttemplate.navigation.dialogs
import app.futured.androidprojecttemplate.navigation.screens

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: NavRouter = remember { NavRouterImpl(navController) },
) {
    LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.let {
        navController.navigateUp()
    }

    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
    ) {
        // Destinations without navbar at the bottom
        screens.forEach { destination ->
            composableScreen(destination) { destination.destinationScreen(navigation) }
        }

        // Dialogs
        dialogs.forEach { destination ->
            composableDialog(destination) { destination.destinationScreen(navigation) }
        }
    }
}
