package com.sirekanian.spacetime.ext

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable

@Composable
fun DefaultAnimatedVisibility(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(visible, enter = fadeIn(), exit = fadeOut()) {
        content()
    }
}

@Composable
fun ScreenAnimatedVisibility(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(visible, enter = fadeIn() + slideInVertically { it / 8 }, exit = fadeOut()) {
        content()
    }
}
