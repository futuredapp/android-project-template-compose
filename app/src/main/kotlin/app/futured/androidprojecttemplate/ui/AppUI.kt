package app.futured.androidprojecttemplate.ui

import androidx.compose.runtime.Composable
import app.futured.androidprojecttemplate.ui.theme.AppTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun AppUI() {
    AppTheme {
        NavGraph()
    }
}
