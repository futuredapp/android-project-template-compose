package app.futured.androidprojecttemplate.ui.screens.detail

import app.futured.arkitekt.compose.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(@Assisted val args: DetailScreenArgs, override val viewState: DetailViewState) :
    BaseViewModel<DetailViewState>(),
    Detail.Actions {

    @AssistedFactory
    interface Factory {
        fun create(args: DetailScreenArgs): DetailViewModel
    }

    init {
        initDetail()
    }

    override fun onNavigateBack() {
        sendEvent(NavigateBackEvent)
    }

    private fun initDetail() = with(viewState) {
        title = args.title
        subtitle = args.subtitle
        value = args.value
    }
}
