package app.futured.androidprojecttemplate.ui.screens.login

import app.futured.androidprojecttemplate.domain.usecase.SignInUseCase
import app.futured.arkitekt.compose.BaseViewModel
import app.futured.arkitekt.crusecases.execute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(override val viewState: LoginViewState, private val signInUseCase: SignInUseCase) :
    BaseViewModel<LoginViewState>(),
    Login.Actions {

    override fun onSignIn() {
        signInUseCase.execute {
            onSuccess { sendEvent(NavigateToHomeEvent) }
        }
    }
}
