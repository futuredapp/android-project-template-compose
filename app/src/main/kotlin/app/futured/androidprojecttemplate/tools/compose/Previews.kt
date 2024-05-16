@file:Suppress("TopLevelPropertyNaming")

package app.futured.androidprojecttemplate.tools.compose

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

private const val PHONE_XHDPI = "spec:id=reference_phone,shape=Normal,width=618,height=1098,unit=dp,dpi=320"
private const val PHONE_XXHDPI = "spec:id=reference_phone,shape=Normal,width=412,height=732,unit=dp,dpi=480"
private const val PHONE_XXXHDPI = "spec:id=reference_phone,shape=Normal,width=316,height=550,unit=dp,dpi=640"

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

@Preview(device = PHONE_XHDPI, name = "7 - PHONE PIXEL XHDPI", group = "phoneScreenDensity")
annotation class PhoneScreenDensityXHDPIPreview

@Preview(device = PHONE_XXHDPI, name = "8 - PHONE PIXEL XXHDPI", group = "phoneScreenDensity")
annotation class PhoneScreenDensityXXHDPIPreview

@Preview(device = PHONE_XXXHDPI, name = "9 - PHONE PIXEL XXXHDPI", group = "phoneScreenDensity")
annotation class PhoneScreenDensityXXXHDPIPreview

@Preview(
    device = PHONE_XXHDPI,
    fontScale = 1.5f,
    name = "5 - Large screen density font scale",
    group = "phoneScreenDensityFontScale",
)
@Preview(
    device = PHONE_XXXHDPI,
    fontScale = 2.0f,
    name = "6 - Largest screen density font scale",
    group = "phoneScreenDensityFontScale",
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
