@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.ui.components.Showcase
import app.futured.androidprojecttemplate.ui.components.layout.VerticalSpacer
import app.futured.androidprojecttemplate.ui.theme.Grid
import app.futured.arkitekt.compose.EventsEffect
import app.futured.arkitekt.compose.onEvent

@Composable
fun LoginScreen(
    navigation: NavRouter,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToHomeEvent> {
                navigation.navigateToHome()
            }
        }

        Login.Content(this)
    }
}

object Login {

    @Stable
    interface Actions {
        fun onSignIn()
    }

    @Composable
    fun Content(
        actions: Actions,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            modifier = modifier,
        ) { contentPadding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            ) {
                Text(text = "Welcome to sample app!")
                VerticalSpacer(Grid.d4)
                Button(onClick = actions::onSignIn) {
                    Text(text = "Sign in")
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun LoginContentPreview() {
    Showcase {
        Login.Content(
            actions = object : Login.Actions {
                override fun onSignIn() = Unit
            },
        )
    }
}
