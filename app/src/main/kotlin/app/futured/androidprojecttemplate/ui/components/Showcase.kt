package app.futured.androidprojecttemplate.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import app.futured.androidprojecttemplate.ui.theme.AppTheme

/**
 * Preview surface wrapper
 */
@Composable
fun Showcase(content: @Composable () -> Unit) {
    AppTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}
