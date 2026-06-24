package app.futured.androidprojecttemplate.ui.screens.third

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ThirdViewState @Inject constructor() : ViewState {

    var item: String by mutableStateOf("")
}
