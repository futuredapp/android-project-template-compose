package app.futured.androidprojecttemplate.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.futured.androidprojecttemplate.ui.theme.AppTheme

/**
 * Preview surface wrapper
 */
@Composable
fun Showcase(modifier: Modifier = Modifier, darkMode: Boolean = false, content: @Composable () -> Unit) {
    AppTheme(darkMode) {
        Surface(color = MaterialTheme.colors.background, modifier = modifier) {
            content()
        }
    }
}
