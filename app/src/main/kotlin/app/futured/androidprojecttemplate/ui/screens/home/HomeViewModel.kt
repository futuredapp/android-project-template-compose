package app.futured.androidprojecttemplate.ui.screens.home

import app.futured.androidprojecttemplate.domain.usecase.LogoutUseCase
import app.futured.androidprojecttemplate.ui.screens.detail.DetailScreenArgs
import app.futured.arkitekt.compose.BaseViewModel
import app.futured.arkitekt.crusecases.execute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(override val viewState: HomeViewState, private val logoutUseCase: LogoutUseCase) :
    BaseViewModel<HomeViewState>(),
    Home.Actions {

    override fun onIncrementCounter() {
        viewState.counter++
    }

    override fun onLogout() {
        logoutUseCase.execute {
            onSuccess { sendEvent(NavigateToLoginEvent) }
        }
    }

    override fun onNavigateToDetail() {
        sendEvent(
            NavigateToDetailEvent(
                DetailScreenArgs(
                    title = "Demo",
                    subtitle = "Subtitle",
                    value = "Demo Subtitle",
                ),
            ),
        )
    }
}
