package app.futured.androidprojecttemplate.ui.screens.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.futured.androidprojecttemplate.navigation.NavRouter
import app.futured.androidprojecttemplate.tools.arch.EventsEffect
import app.futured.androidprojecttemplate.tools.arch.onEvent
import app.futured.androidprojecttemplate.tools.compose.ScreenPreviews
import app.futured.androidprojecttemplate.tools.extensions.bottomSheetSize
import app.futured.androidprojecttemplate.ui.components.Showcase
import app.futured.androidprojecttemplate.ui.theme.Grid

@Composable
fun InfoScreen(
    navigation: NavRouter,
    viewModel: InfoViewModel = hiltViewModel(),
) {
    with(viewModel) {
        EventsEffect {
            onEvent<NavigateBackEvent> {
                navigation.popBackStack()
            }
        }

        Info.Content(
            actions = this,
        )
    }
}

object Info {

    @Stable
    interface Actions {
        fun onNavigateBack()
    }

    @Composable
    fun Content(
        actions: Actions,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier.bottomSheetSize(),
        ) {
            Text(
                modifier = Modifier.padding(Grid.d4),
                text =
                "This is a BottomSheet, a commonly used user interface component in Android and other mobile operating systems." +
                    " It is used to display content that slides up from the bottom of the screen, providing users with additional choices and " +
                    "actions. This particular BottomSheet is part of a mock-up, serving purely for testing purposes. " +
                    "Please note that the text displayed here is only placeholder text and does not represent any actual content or functionalities. " +
                    "The aim of this testing is to validate the design, layout, and overall user experience of the BottomSheet component.",
            )
        }
    }
}

@ScreenPreviews
@Composable
fun InfoContentPreview() = Showcase {
    Info.Content(
        actions = object : Info.Actions {
            override fun onNavigateBack() = Unit
        },
    )
}
