package app.futured.androidprojecttemplate.ui.screens.second

import app.futured.androidprojecttemplate.ui.screens.picker.PickerScreenArgs
import app.futured.androidprojecttemplate.ui.screens.third.ThirdScreenArgs
import app.futured.arkitekt.core.event.Event

sealed class SecondEvents : Event<SecondViewState>()

data class NavigateToPickerEvent(val args: PickerScreenArgs) : SecondEvents()
data class NavigateToThirdEvent(val args: ThirdScreenArgs) : SecondEvents()
data object NavigateBackEvent : SecondEvents()
