@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun ProfileScreen(
    navigation: NavRouter,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToLoginEvent> {
                navigation.navigateToLogin()
            }
        }

        Profile.Content(this)
    }
}

object Profile {

    @Stable
    interface Actions {
        fun onSignOut()
    }

    @Composable
    fun Content(
        actions: Actions,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = stringResource(R.string.profile_title)) }) },
            modifier = modifier,
        ) { contentPadding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            ) {
                Button(onClick = actions::onSignOut) {
                    Text(text = stringResource(R.string.profile_sign_out))
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun ProfileContentPreview() = Showcase {
    Profile.Content(
        actions = object : Profile.Actions {
            override fun onSignOut() = Unit
        },
    )
}
