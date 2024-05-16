@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.tools.arch.EventsEffect
import app.futured.androidprojecttemplate.tools.arch.onEvent
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.ui.components.AddFloatingActionButton
import app.futured.androidprojecttemplate.ui.components.Showcase

@Composable
fun HomeScreen(
    navigation: NavRouter,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToDetailEvent> {
                navigation.navigateToDetail(
                    title = "Demo",
                    subtitle = "Subtitle",
                    value = "Demo Subtitle",
                )
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
            }
        }
    }
}

@ScreenPreviews
@Composable
fun HomeContentPreview() {
    Showcase {
        Home.Content(
            actions =
            object : Home.Actions {
                override fun onNavigateToDetail() = Unit
                override fun onIncrementCounter() = Unit
            },
            counter = 5,
        )
    }
}
