package app.futured.androidprojecttemplate.ui.screens.login

import app.futured.arkitekt.core.event.Event

sealed class LoginEvents : Event<LoginViewState>()

data object NavigateToFirstEvent : LoginEvents()
