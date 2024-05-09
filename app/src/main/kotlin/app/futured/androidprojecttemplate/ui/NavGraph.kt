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
import app.futured.androidprojecttemplate.navigation.bottomSheetDialogs
import app.futured.androidprojecttemplate.navigation.composableBottomSheetDialog
import app.futured.androidprojecttemplate.navigation.composableDialog
import app.futured.androidprojecttemplate.navigation.composableScreen
import app.futured.androidprojecttemplate.navigation.dialogs
import app.futured.androidprojecttemplate.navigation.screens
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun NavGraph(
    bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator(),
    navController: NavHostController = rememberNavController(bottomSheetNavigator),
    navigation: NavRouter = remember { NavRouterImpl(navController) },
) {
    LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.let {
        navController.popBackStack()
    }

    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
    ) {
        // Destinations without navbar at the bottom
        screens.forEach { destination ->
            composableScreen(destination) { destination.destinationScreen(navigation) }
        }

        // Bottom sheet dialogs
        bottomSheetDialogs.forEach { destination ->
            composableBottomSheetDialog(destination) { destination.destinationScreen(navigation) }
        }

        // Dialogs
        dialogs.forEach { destination ->
            composableDialog(destination) { destination.destinationScreen(navigation) }
        }
    }
}
