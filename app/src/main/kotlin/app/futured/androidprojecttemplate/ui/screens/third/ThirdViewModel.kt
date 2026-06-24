package app.futured.androidprojecttemplate.ui.screens.third

import app.futured.arkitekt.compose.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ThirdViewModel.Factory::class)
class ThirdViewModel @AssistedInject constructor(@Assisted val args: ThirdScreenArgs, override val viewState: ThirdViewState) :
    BaseViewModel<ThirdViewState>(),
    Third.Actions {

    @AssistedFactory
    interface Factory {
        fun create(args: ThirdScreenArgs): ThirdViewModel
    }

    init {
        viewState.item = args.item
    }

    override fun onNavigateBack() {
        sendEvent(NavigateBackEvent)
    }
}
