package com.sirekanian.spacetime.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sirekanian.spacetime.MainState

@Composable
fun GalleryPageContent(state: MainState) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(100) {
            val name = "item${it + 1}"
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable { state.addPage(name) },
                contentAlignment = Alignment.Center,
            ) {
                Text(text = name)
            }
        }
    }
}
