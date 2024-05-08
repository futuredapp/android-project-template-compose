@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.androidprojecttemplate.navigation.NavigationDestinations
import app.futured.androidprojecttemplate.tools.arch.EventsEffect
import app.futured.androidprojecttemplate.tools.arch.onEvent
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.ui.components.AddFloatingActionButton
import app.futured.androidprojecttemplate.ui.components.Showcase

@Composable
fun DetailScreen(
    navigation: NavigationDestinations,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateBackEvent> {
                navigation.popBackStack()
            }
        }

        Detail.Content(
            this,
            viewState.counter,
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
        counter: Int,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "DetailScreen") },
                    navigationIcon = {
                        IconButton(
                            onClick = { actions.navigateBack() },
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
                        }
                    },
                )
            },
            floatingActionButton = {
                AddFloatingActionButton(
                    onClick = {
                        actions.incrementCounter()
                    },
                )
            },
            modifier = modifier,
        ) { contentPadding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
            ) {
                Text(text = "Detail: $counter")
            }
        }
    }
}

@ScreenPreviews
@Composable
fun DetailContentPreview() {
    Showcase {
        Detail.Content(
            Detail.PreviewActions,
            counter = 5,
        )
    }
}
