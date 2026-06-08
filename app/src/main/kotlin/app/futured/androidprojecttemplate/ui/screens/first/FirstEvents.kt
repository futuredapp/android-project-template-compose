package app.futured.androidprojecttemplate.ui.screens.first

import app.futured.arkitekt.core.event.Event

sealed class FirstEvents : Event<FirstViewState>()

data object NavigateToSecondEvent : FirstEvents()
data class ShowToastEvent(val text: String) : FirstEvents()
