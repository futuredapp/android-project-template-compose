package app.futured.androidprojecttemplate.ui.screens.detail

import app.futured.arkitekt.compose.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(override val viewState: DetailViewState) :
    BaseViewModel<DetailViewState>(),
    Detail.Actions {

    override fun onNavigateBack() {
        sendEvent(NavigateBackEvent)
    }

    override fun onIncrementCounter() {
        viewState.counter++
    }
}
