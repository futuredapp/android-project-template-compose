package app.futured.androidprojecttemplate.ui.screens.picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
fun PickerScreen(
    args: PickerScreenArgs,
    navigation: NavRouter,
    viewModel: PickerViewModel = hiltViewModel<PickerViewModel, PickerViewModel.Factory>(
        creationCallback = { factory -> factory.create(args) },
    ),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<PickItemEvent> {
                navigation.navigateBackWithResult(PICKED_ITEM_KEY, it.item)
            }
        }

        Picker.Content(viewState, this)
    }
}

object Picker {

    @Stable
    interface Actions {
        fun onItemSelected(item: String)
    }

    @Composable
    fun Content(
        viewState: PickerViewState,
        actions: Actions,
        modifier: Modifier = Modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.picker_title), style = MaterialTheme.typography.titleLarge)
            VerticalSpacer(Grid.d2)
            viewState.items.forEach { itemRes ->
                val label = stringResource(itemRes)
                ListItem(
                    headlineContent = {
                        Text(label)
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            actions.onItemSelected(label)
                        },
                )
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun PickerContentPreview() = Showcase {
    Picker.Content(
        viewState = PickerViewState().apply {
            items = listOf(R.string.fruit_apple, R.string.fruit_banana, R.string.fruit_orange)
        },
        actions = object : Picker.Actions {
            override fun onItemSelected(item: String) = Unit
        },
    )
}
