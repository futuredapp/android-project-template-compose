package app.futured.androidprojecttemplate.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavBackStackEntry
import app.futured.androidprojecttemplate.tools.Constants

object Transitions {
    enum class RoutePrefix {
        MODAL_,
        NORMAL_,
        BOTTOM_NAV_,
    }

    val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = {
        when (targetState.transitionPrefix()) {
            RoutePrefix.NORMAL_ ->
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(Constants.Ui.SCREEN_ANIMATION_DURATION),
                )

            RoutePrefix.MODAL_ ->
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(Constants.Ui.SCREEN_ANIMATION_DURATION),
                )

            RoutePrefix.BOTTOM_NAV_ -> fadeIn(animationSpec = tween(Constants.Ui.SCREEN_FADE_DURATION))
        }
    }
    val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = {
        when (targetState.transitionPrefix()) {
            RoutePrefix.NORMAL_ ->
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(Constants.Ui.SCREEN_ANIMATION_DURATION),
                )

            RoutePrefix.MODAL_ ->
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(Constants.Ui.SCREEN_ANIMATION_DURATION),
                )

            RoutePrefix.BOTTOM_NAV_ -> fadeOut(tween(Constants.Ui.SCREEN_FADE_DURATION))
        }
    }

    val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        when (initialState.transitionPrefix()) {
            RoutePrefix.NORMAL_ ->
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(Constants.Ui.SCREEN_ANIMATION_DURATION),
                )

            RoutePrefix.MODAL_ ->
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(Constants.Ui.SCREEN_ANIMATION_DURATION),
                )

            RoutePrefix.BOTTOM_NAV_ -> fadeIn(tween(Constants.Ui.SCREEN_FADE_DURATION))
        }
    }

    val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
        when (initialState.transitionPrefix()) {
            RoutePrefix.NORMAL_ ->
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(Constants.Ui.SCREEN_ANIMATION_DURATION),
                )

            RoutePrefix.MODAL_ ->
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(Constants.Ui.SCREEN_ANIMATION_DURATION),
                )

            RoutePrefix.BOTTOM_NAV_ -> fadeOut(tween(Constants.Ui.SCREEN_FADE_DURATION))
        }
    }
}

private fun NavBackStackEntry.transitionPrefix(): Transitions.RoutePrefix =
    when {
        this.destination.route!!.startsWith("${Transitions.RoutePrefix.MODAL_}") -> Transitions.RoutePrefix.MODAL_
        this.destination.route!!.startsWith("${Transitions.RoutePrefix.NORMAL_}") -> Transitions.RoutePrefix.NORMAL_
        this.destination.route!!.startsWith("${Transitions.RoutePrefix.BOTTOM_NAV_}") -> Transitions.RoutePrefix.BOTTOM_NAV_
        else -> throw IllegalStateException("Unknown transition prefix")
    }
