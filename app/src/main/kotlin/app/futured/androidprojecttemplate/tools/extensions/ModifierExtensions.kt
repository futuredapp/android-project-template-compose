package app.futured.androidprojecttemplate.tools.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("ComposeModifierWithoutDefault", "ComposeComposableModifier")
@Composable
fun Modifier.bottomSheetSize(
    maxHeight: Dp = LocalConfiguration.current.screenHeightDp.dp,
): Modifier =
    fillMaxWidth()
        .wrapContentSize()
        .heightIn(max = maxHeight)
        .navigationBarsPadding()
