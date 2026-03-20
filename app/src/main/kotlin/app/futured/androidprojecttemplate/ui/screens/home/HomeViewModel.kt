package app.futured.androidprojecttemplate.ui.screens.home

import app.futured.arkitekt.compose.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(override val viewState: HomeViewState) :
    BaseViewModel<HomeViewState>(),
    Home.Actions {

    override fun onIncrementCounter() {
        viewState.counter++
    }

    override fun onNavigateToDetail() {
        sendEvent(NavigateToDetailEvent)
    }
}
