package app.futured.androidprojecttemplate.ui

import app.futured.androidprojecttemplate.domain.usecase.GetInitialDestinationUseCase
import app.futured.androidprojecttemplate.navigation.MainRoute
import app.futured.arkitekt.compose.BaseViewModel
import app.futured.arkitekt.core.ViewState
import app.futured.arkitekt.core.event.Event
import app.futured.arkitekt.crusecases.execute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    override val viewState: AppViewState,
    private val getInitialDestinationUseCase: GetInitialDestinationUseCase,
) : BaseViewModel<AppViewState>() {

    init {
        getInitialDestination()
    }

    private fun getInitialDestination() {
        getInitialDestinationUseCase.execute {
            onSuccess { destination ->
                if (destination == MainRoute.Home) {
                    sendEvent(NavigateToHomeEvent)
                }
            }
        }
    }
}

@ViewModelScoped
class AppViewState @Inject constructor() : ViewState

sealed class AppEvent : Event<AppViewState>()

data object NavigateToHomeEvent : AppEvent()
