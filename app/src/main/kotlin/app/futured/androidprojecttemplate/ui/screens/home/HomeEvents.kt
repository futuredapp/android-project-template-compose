package app.futured.androidprojecttemplate.ui.screens.home

import app.futured.androidprojecttemplate.ui.screens.detail.DetailScreenArgs
import app.futured.arkitekt.core.event.Event

sealed class HomeEvents : Event<HomeViewState>()

data class NavigateToDetailEvent(val args: DetailScreenArgs) : HomeEvents()
data object NavigateToLoginEvent : HomeEvents()
