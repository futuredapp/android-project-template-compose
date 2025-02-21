@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("PackageNaming")

package app.futured.androidprojecttemplate.ui.screens._templateScreen

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
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.tools.arch.BaseViewModel
import app.futured.androidprojecttemplate.tools.arch.EventsEffect
import app.futured.androidprojecttemplate.tools.arch.onEvent
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.ui.components.Showcase
import app.futured.arkitekt.core.ViewState
import app.futured.arkitekt.core.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * This is a template for creating new screens:
 *
 * 1. Copy wherever you wanna create the screen.
 * 2. Select all occurrences of "TEMPLATE" (Ctrl + G) and rename to your liking.
 * 3. Extract all parts to respective files (alt+enter on interface/class signature -> extract from file).
 */
@Composable
fun TEMPLATEScreen(
    navigation: NavRouter,
    viewModel: TEMPLATEViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateBackEvent> {
                navigation.popBackStack()
            }
        }

        TEMPLATE.Content(
            actions = this,
        )
    }
}

object TEMPLATE {
    @Stable
    interface Actions {
        fun onNavigateBack()
    }

    @Composable
    fun Content(
        actions: Actions,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "TEMPLATEScreen") },
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
                Text(text = "TEMPLATE")
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun TEMPLATEContentPreview() = Showcase {
    TEMPLATE.Content(
        actions = object : TEMPLATE.Actions {
            override fun onNavigateBack() = Unit
        },
    )
}

sealed class TEMPLATEEvent : Event<TEMPLATEViewState>()

data object NavigateBackEvent : TEMPLATEEvent()

@HiltViewModel
class TEMPLATEViewModel @Inject constructor(
    override val viewState: TEMPLATEViewState,
) : BaseViewModel<TEMPLATEViewState>(), TEMPLATE.Actions {
    override fun onNavigateBack() {
        sendEvent(NavigateBackEvent)
    }
}

@ViewModelScoped
class TEMPLATEViewState @Inject constructor() : ViewState
