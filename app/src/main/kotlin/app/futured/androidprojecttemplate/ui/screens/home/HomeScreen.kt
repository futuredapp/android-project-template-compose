@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.ui.components.AddFloatingActionButton
import app.futured.androidprojecttemplate.ui.components.Showcase
import app.futured.androidprojecttemplate.ui.theme.Grid
import app.futured.arkitekt.compose.EventsEffect
import app.futured.arkitekt.compose.onEvent

@Composable
fun HomeScreen(
    navigation: NavRouter,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToDetailEvent> {
                navigation.navigateToDetail(it.args)
            }
            onEvent<NavigateToLoginEvent> {
                navigation.navigateToLogin()
            }
        }

        Home.Content(
            viewModel,
            viewState.counter,
        )
    }
}

object Home {

    @Stable
    interface Actions {
        fun onNavigateToDetail()
        fun onIncrementCounter()
        fun onLogout()
    }

    @Composable
    fun Content(
        actions: Actions,
        counter: Int,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "HomeScreen") }) },
            floatingActionButton = {
                AddFloatingActionButton(
                    onClick = {
                        actions.onIncrementCounter()
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
                    .padding(contentPadding)
                    .clickable {
                        actions.onNavigateToDetail()
                    },
            ) {
                Text(text = "Home: $counter")

                Spacer(modifier = Modifier.height(Grid.d4))

                Button(onClick = actions::onLogout) {
                    Text(text = "Logout")
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun HomeContentPreview() {
    Showcase {
        Home.Content(
            actions = object : Home.Actions {
                override fun onNavigateToDetail() = Unit
                override fun onIncrementCounter() = Unit
                override fun onLogout() = Unit
            },
            counter = 5,
        )
    }
}
