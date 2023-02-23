package app.futured.androidprojecttemplate.tools.compose

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Dark mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Light mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
annotation class DarkLightPreviews

@Preview(
    name = "Small font",
    group = "Font scales",
    fontScale = 0.5f
)
@Preview(
    name = "Large font",
    group = "Font scales",
    fontScale = 1.5f
)
annotation class FontScalePreviews

@Preview(
    name = "Big device",
    group = "Device sizes",
    device = Devices.PIXEL_C
)
@Preview(
    name = "Small device",
    group = "Device sizes",
    device = Devices.PIXEL
)
annotation class DeviceSizePreviews
