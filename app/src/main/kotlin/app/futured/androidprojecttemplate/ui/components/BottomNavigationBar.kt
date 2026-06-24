package app.futured.androidprojecttemplate.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import app.futured.androidprojecttemplate.R
import app.futured.androidprojecttemplate.navigation.MainRoute

/**
 * Bottom navigation tabs. Each tab maps to its root [MainRoute] together with the label and icon
 * shown in the [BottomNavigationBar].
 */
enum class BottomNavTab(val rootRoute: MainRoute, @StringRes val label: Int, val icon: ImageVector) {
    Home(MainRoute.First, R.string.tab_home, Icons.Filled.Home),
    Profile(MainRoute.Profile, R.string.tab_profile, Icons.Filled.Person),
}

@Composable
fun BottomNavigationBar(
    currentRoute: NavKey?,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        BottomNavTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.rootRoute,
                onClick = { onTabSelected(tab) },
                icon = { Icon(tab.icon, contentDescription = stringResource(tab.label)) },
                label = { Text(text = stringResource(tab.label)) },
            )
        }
    }
}
