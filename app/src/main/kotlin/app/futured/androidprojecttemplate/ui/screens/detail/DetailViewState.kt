package app.futured.androidprojecttemplate.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DetailViewState @Inject constructor() : ViewState {

    var title by mutableStateOf("")
    var subtitle by mutableStateOf<String?>(null)
    var value by mutableStateOf<String?>(null)
}
