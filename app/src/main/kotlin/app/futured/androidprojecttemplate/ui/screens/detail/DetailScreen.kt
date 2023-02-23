package app.futured.androidprojecttemplate.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.androidprojecttemplate.navigation.NavigationDestinations
import app.futured.androidprojecttemplate.tools.arch.EventsEffect
import app.futured.androidprojecttemplate.tools.arch.onEvent
import app.futured.androidprojecttemplate.tools.compose.DarkLightPreviews
import app.futured.androidprojecttemplate.tools.compose.DeviceSizePreviews
import app.futured.androidprojecttemplate.ui.components.AddFloatingActionButton
import app.futured.androidprojecttemplate.ui.components.Showcase

@Composable
fun DetailScreen(
    navigation: NavigationDestinations,
    viewModel: DetailViewModel = hiltViewModel()
) {
    EventsEffect(viewModel) {
        onEvent<NavigateBackEvent> {
            navigation.popBackStack()
        }
    }

    with(viewModel.viewState) {
        Detail.Content(
            viewModel,
            counter
        )
    }
}

object Detail {

    interface Actions {
        fun navigateBack() = Unit
        fun incrementCounter() = Unit
    }

    object PreviewActions : Actions

    @Composable
    fun Content(
        actions: Actions,
        counter: Int
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "DetailScreen") },
                    navigationIcon = {
                        IconButton(
                            onClick = { actions.navigateBack() },
                        ) {
                            Icon(Icons.Default.ArrowBack, "")
                        }
                    }
                )
            },
            floatingActionButton = {
                AddFloatingActionButton(
                    onClick = {
                        actions.incrementCounter()
                    }
                )
            }
        ) { contentPadding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            ) {
                Text(text = "Detail: $counter")
            }
        }
    }
}

@DarkLightPreviews
@DeviceSizePreviews
@Composable
fun DetailContentPreview() {
    Showcase {
        Detail.Content(
            Detail.PreviewActions,
            counter = 5,
        )
    }
}
