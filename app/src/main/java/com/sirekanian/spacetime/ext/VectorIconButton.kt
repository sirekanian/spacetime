package com.sirekanian.spacetime.ext

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.sirekanian.spacetime.D

@Composable
fun VectorIconButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(onClick, Modifier.size(D.toolbarHeight)) {
        Icon(icon, null)
    }
}
