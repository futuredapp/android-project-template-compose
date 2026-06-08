package app.futured.androidprojecttemplate.ui.screens.first

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.futured.arkitekt.core.ViewState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FirstViewState @Inject constructor() : ViewState {

    var createdAt by mutableStateOf("")
    var counter by mutableLongStateOf(0)
    var randomPerson: String? by mutableStateOf(null)
}
