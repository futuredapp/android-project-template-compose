package app.futured.androidprojecttemplate.ui.screens.profile

import app.futured.androidprojecttemplate.domain.usecase.LogoutUseCase
import app.futured.arkitekt.compose.BaseViewModel
import app.futured.arkitekt.crusecases.execute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(override val viewState: ProfileViewState, private val logoutUseCase: LogoutUseCase) :
    BaseViewModel<ProfileViewState>(),
    Profile.Actions {

    override fun onSignOut() {
        logoutUseCase.execute {
            onSuccess { sendEvent(NavigateToLoginEvent) }
        }
    }
}
