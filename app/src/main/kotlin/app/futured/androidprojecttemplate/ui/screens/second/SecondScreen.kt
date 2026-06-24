@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.androidprojecttemplate.ui.screens.second

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import app.futured.androidprojecttemplate.R
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.ui.LocalResultStore
import app.futured.androidprojecttemplate.ui.components.Showcase
import app.futured.androidprojecttemplate.ui.components.layout.Spacer
import app.futured.androidprojecttemplate.ui.screens.picker.PICKED_ITEM_KEY
import app.futured.androidprojecttemplate.ui.theme.Grid
import app.futured.arkitekt.compose.EventsEffect
import app.futured.arkitekt.compose.onEvent

@Composable
fun SecondScreen(
    navigation: NavRouter,
    viewModel: SecondViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateToPickerEvent> {
                navigation.navigateToPicker(it.args)
            }
            onEvent<NavigateToThirdEvent> {
                navigation.navigateToThird(it.args)
            }
            onEvent<NavigateBackEvent> {
                navigation.popBackStack()
            }
        }

        // Observe the item returned by the picker bottom sheet and forward it to the ViewModel.
        val resultStore = LocalResultStore.current
        val pickedItem = resultStore?.get<String>(PICKED_ITEM_KEY)
        LaunchedEffect(pickedItem) {
            if (pickedItem != null) {
                resultStore.consume<String>(PICKED_ITEM_KEY)
                onItemPicked(pickedItem)
            }
        }

        Second.Content(this)
    }
}

object Second {

    @Stable
    interface Actions {
        fun onPickFruit()
        fun onPickVeggie()
        fun onItemPicked(item: String)
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
                    title = { Text(text = stringResource(R.string.second_title)) },
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
                Button(onClick = actions::onPickFruit) {
                    Text(text = stringResource(R.string.second_pick_fruit))
                }
                Spacer(Grid.d2)
                Button(onClick = actions::onPickVeggie) {
                    Text(text = stringResource(R.string.second_pick_veggie))
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun SecondContentPreview() = Showcase {
    Second.Content(
        actions = object : Second.Actions {
            override fun onPickFruit() = Unit
            override fun onPickVeggie() = Unit
            override fun onItemPicked(item: String) = Unit
            override fun onNavigateBack() = Unit
        },
    )
}
