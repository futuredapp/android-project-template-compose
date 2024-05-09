package app.futured.androidprojecttemplate.ui.screens.detail

import app.futured.arkitekt.core.event.Event

sealed class DetailEvents : Event<DetailViewState>()
data object NavigateBackEvent : DetailEvents()

data object NavigateToInfoEvent : DetailEvents()
