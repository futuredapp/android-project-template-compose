package app.futured.androidprojecttemplate.ui.screens.third

import app.futured.arkitekt.core.event.Event

sealed class ThirdEvents : Event<ThirdViewState>()

data object NavigateBackEvent : ThirdEvents()
