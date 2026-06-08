package app.futured.androidprojecttemplate.ui.screens.picker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class PickerViewState @Inject constructor() : ViewState {

    /** String resource ids of the pickable items. */
    var items: List<Int> by mutableStateOf(emptyList())
}
