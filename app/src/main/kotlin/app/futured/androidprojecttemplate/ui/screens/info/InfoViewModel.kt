package app.futured.androidprojecttemplate.ui.screens.info

import app.futured.androidprojecttemplate.tools.arch.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    override val viewState: InfoViewState,
) : BaseViewModel<InfoViewState>(), Info.Actions {
    override fun onNavigateBack() {
        sendEvent(NavigateBackEvent)
    }
}
