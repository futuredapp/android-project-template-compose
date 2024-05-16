package app.futured.androidprojecttemplate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColorScheme(
    primary = orange300,
    primaryContainer = orange100,
    secondary = blue400,
    secondaryContainer = blue200,
    background = cloud50,
    surface = orange100,
    onPrimary = pureWhite,
    onSecondary = pureWhite,
    onBackground = black900,
    onSurface = black900,
)

private val DarkColorPalette = darkColorScheme(
    primary = orange300,
    primaryContainer = orange300,
    secondary = blue400,
    secondaryContainer = blue200,
    background = black900,
    surface = black700,
    onPrimary = pureWhite,
    onSecondary = pureWhite,
    onBackground = pureWhite,
    onSurface = pureWhite,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content,
    )
}
