package app.futured.androidprojecttemplate.ui.screens.profile

import app.futured.arkitekt.core.event.Event

sealed class ProfileEvents : Event<ProfileViewState>()

data object NavigateToLoginEvent : ProfileEvents()
