package app.futured.androidprojecttemplate.ui.screens.info

import app.futured.arkitekt.core.event.Event

sealed class InfoEvent : Event<InfoViewState>()

data object NavigateBackEvent : InfoEvent()
