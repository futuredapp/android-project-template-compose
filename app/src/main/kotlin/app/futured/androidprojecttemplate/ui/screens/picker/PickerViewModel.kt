package app.futured.androidprojecttemplate.ui.screens.picker

import app.futured.androidprojecttemplate.R
import app.futured.arkitekt.compose.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = PickerViewModel.Factory::class)
class PickerViewModel @AssistedInject constructor(@Assisted val args: PickerScreenArgs, override val viewState: PickerViewState) :
    BaseViewModel<PickerViewState>(),
    Picker.Actions {

    @AssistedFactory
    interface Factory {
        fun create(args: PickerScreenArgs): PickerViewModel
    }

    init {
        viewState.items = when (args.type) {
            PickerType.Fruit -> listOf(R.string.fruit_apple, R.string.fruit_banana, R.string.fruit_orange)
            PickerType.Veggie -> listOf(
                R.string.veggie_carrot,
                R.string.veggie_pepper,
                R.string.veggie_onion,
                R.string.veggie_chilli,
            )
        }
    }

    override fun onItemSelected(item: String) {
        sendEvent(PickItemEvent(item))
    }
}
