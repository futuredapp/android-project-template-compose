package app.futured.androidprojecttemplate.ui.screens.second

import app.futured.androidprojecttemplate.ui.screens.picker.PickerScreenArgs
import app.futured.androidprojecttemplate.ui.screens.picker.PickerType
import app.futured.androidprojecttemplate.ui.screens.third.ThirdScreenArgs
import app.futured.arkitekt.compose.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(override val viewState: SecondViewState) :
    BaseViewModel<SecondViewState>(),
    Second.Actions {

    override fun onPickFruit() {
        sendEvent(NavigateToPickerEvent(PickerScreenArgs(PickerType.Fruit)))
    }

    override fun onPickVeggie() {
        sendEvent(NavigateToPickerEvent(PickerScreenArgs(PickerType.Veggie)))
    }

    override fun onItemPicked(item: String) {
        sendEvent(NavigateToThirdEvent(ThirdScreenArgs(item)))
    }

    override fun onNavigateBack() {
        sendEvent(NavigateBackEvent)
    }
}
