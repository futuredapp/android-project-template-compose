@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui.screens.third

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import app.futured.androidprojecttemplate.R
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.ui.components.Showcase
import app.futured.arkitekt.compose.EventsEffect
import app.futured.arkitekt.compose.onEvent

@Composable
fun ThirdScreen(
    args: ThirdScreenArgs,
    navigation: NavRouter,
    viewModel: ThirdViewModel = hiltViewModel<ThirdViewModel, ThirdViewModel.Factory>(
        creationCallback = { factory -> factory.create(args) },
    ),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateBackEvent> {
                navigation.popBackStack()
            }
        }

        Third.Content(viewState, this)
    }
}

object Third {

    @Stable
    interface Actions {
        fun onNavigateBack()
    }

    @Composable
    fun Content(
        viewState: ThirdViewState,
        actions: Actions,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.third_title)) },
                    navigationIcon = {
                        IconButton(onClick = { actions.onNavigateBack() }) {
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
                Text(
                    text = stringResource(R.string.third_selected_item, viewState.item),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun ThirdContentPreview() = Showcase {
    Third.Content(
        viewState = ThirdViewState().apply { item = "🍎 Apple" },
        actions = object : Third.Actions {
            override fun onNavigateBack() = Unit
        },
    )
}
