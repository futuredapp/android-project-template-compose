package app.futured.androidprojecttemplate.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.futured.arkitekt.crusecases.CoroutineScopeOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val viewState: HomeViewState
): ViewModel(), CoroutineScopeOwner {

    override val coroutineScope: CoroutineScope = viewModelScope

    fun incrementCounter() {
        viewState.counter++
    }
}