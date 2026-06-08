@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.ui.components.Showcase
import app.futured.androidprojecttemplate.ui.theme.Grid
import app.futured.arkitekt.compose.EventsEffect
import app.futured.arkitekt.compose.onEvent

@Composable
fun DetailScreen(
    args: DetailScreenArgs,
    navigation: NavRouter,
    viewModel: DetailViewModel = hiltViewModel<DetailViewModel, DetailViewModel.Factory>(
        creationCallback = { factory -> factory.create(args) },
    ),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateBackEvent> {
                navigation.popBackStack()
            }
        }

        Detail.Content(
            viewState,
            this,
        )
    }
}

object Detail {

    @Stable
    interface Actions {
        fun onNavigateBack()
    }

    @Composable
    fun Content(
        viewState: DetailViewState,
        actions: Actions,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "DetailScreen") },
                    navigationIcon = {
                        IconButton(
                            onClick = { actions.onNavigateBack() },
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
                        }
                    },
                )
            },
            modifier = modifier,
        ) { contentPadding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            ) {
                Text(viewState.title, style = MaterialTheme.typography.displaySmall)
                Spacer(Modifier.height(Grid.d4))
                viewState.subtitle?.let { Text(it, style = MaterialTheme.typography.headlineMedium) }
                Spacer(Modifier.height(Grid.d2))
                viewState.value?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun DetailContentPreview() {
    Showcase {
        Detail.Content(
            viewState = DetailViewState().apply {
                title = "DetailScreen"
                subtitle = "Subtitle"
                value = "Value"
            },
            actions = object : Detail.Actions {
                override fun onNavigateBack() = Unit
            },
        )
    }
}
