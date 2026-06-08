@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui.screens.first

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import app.futured.androidprojecttemplate.R
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.ui.components.Showcase
import app.futured.androidprojecttemplate.ui.components.layout.VerticalSpacer
import app.futured.androidprojecttemplate.ui.theme.Grid
import app.futured.arkitekt.compose.EventsEffect
import app.futured.arkitekt.compose.onEvent

@Composable
fun FirstScreen(
    navigation: NavRouter,
    viewModel: FirstViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToSecondEvent> {
                navigation.navigateToSecond()
            }
            onEvent<ShowToastEvent> {
                Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
            }
        }

        First.Content(viewState, this)
    }
}

object First {

    @Stable
    interface Actions {
        fun onContinue()
    }

    @Composable
    fun Content(
        viewState: FirstViewState,
        actions: Actions,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = stringResource(R.string.first_title)) }) },
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
                    text = viewState.createdAt,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(R.string.first_counter, viewState.counter),
                    style = MaterialTheme.typography.bodyLarge,
                )
                AnimatedVisibility(viewState.randomPerson != null) {
                    viewState.randomPerson?.let { person ->
                        Column {
                            VerticalSpacer(Grid.d1)
                            Text(
                                text = stringResource(R.string.first_random_person, person),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                VerticalSpacer(Grid.d4)
                Button(onClick = actions::onContinue) {
                    Text(text = stringResource(R.string.first_continue))
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun FirstContentPreview() = Showcase {
    First.Content(
        viewState = FirstViewState().apply {
            createdAt = "Created at: 14:20:20"
        },
        actions = object : First.Actions {
            override fun onContinue() = Unit
        },
    )
}
