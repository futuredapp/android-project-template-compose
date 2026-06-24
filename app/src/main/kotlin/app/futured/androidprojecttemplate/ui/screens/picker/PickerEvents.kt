package app.futured.androidprojecttemplate.ui.screens.picker

import app.futured.arkitekt.core.event.Event

sealed class PickerEvents : Event<PickerViewState>()

data class PickItemEvent(val item: String) : PickerEvents()
