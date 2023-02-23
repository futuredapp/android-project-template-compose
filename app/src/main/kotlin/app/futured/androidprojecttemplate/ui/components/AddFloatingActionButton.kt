package app.futured.androidprojecttemplate.ui.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import app.futured.androidprojecttemplate.tools.compose.DarkLightPreviews

@Composable
fun AddFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Default.Add, "")
    }
}

@DarkLightPreviews
@Composable
fun AddFloatingActionButtonPreview() {
    Showcase {
        AddFloatingActionButton {}
    }
}
