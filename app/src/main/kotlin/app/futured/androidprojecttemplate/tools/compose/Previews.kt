@file:Suppress("TopLevelPropertyNaming")

package app.futured.androidprojecttemplate.tools.compose

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "1 - Light mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
    name = "2 - Dark mode",
    group = "UI mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
annotation class DarkLightPreviews

@Preview(
    name = "3 - Small font",
    group = "Font scales",
    fontScale = 0.5f,
)
@Preview(
    name = "4 - Large font",
    group = "Font scales",
    fontScale = 1.5f,
)
annotation class FontScalePreviews

@Preview(name = "7 - PHONE PIXEL XHDPI", group = "phoneScreenDensity", device = "spec:width=1080px,height=2340px,dpi=320")
annotation class PhoneScreenDensityXHDPIPreview

@Preview(name = "8 - PHONE PIXEL XXHDPI", group = "phoneScreenDensity", device = "spec:width=1080px,height=2340px,dpi=480")
annotation class PhoneScreenDensityXXHDPIPreview

@Preview(name = "9 - PHONE PIXEL XXXHDPI", group = "phoneScreenDensity", device = "spec:width=1080px,height=2340px,dpi=640")
annotation class PhoneScreenDensityXXXHDPIPreview

@Preview(
    fontScale = 1.5f,
    name = "5 - Large screen density font scale",
    group = "phoneScreenDensityFontScale",
    device = "spec:width=1080px,height=2340px,dpi=480",
)
@Preview(
    fontScale = 2.0f,
    name = "6 - Largest screen density font scale",
    group = "phoneScreenDensityFontScale",
    device = "spec:width=1080px,height=2340px,dpi=640",
)
annotation class ScreenDensityWithFontScalePreviews

@PhoneScreenDensityXHDPIPreview
@PhoneScreenDensityXXHDPIPreview
@PhoneScreenDensityXXXHDPIPreview
annotation class PhoneScreenDensityPreviews

@DarkLightPreviews
@FontScalePreviews
annotation class ComponentPreviews

@DarkLightPreviews
@ScreenDensityWithFontScalePreviews
annotation class ScreenPreviews
