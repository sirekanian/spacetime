package com.sirekanian.spacetime.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sirekanian.spacetime.ImagePage
import com.sirekanian.spacetime.MainState

@Composable
fun ImagePageContent(insets: PaddingValues, state: MainState, page: ImagePage, index: Int) {
    AsyncImage(
        model = page.name,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
    Button(
        onClick = { state.removePage(index) },
        modifier = Modifier
            .padding(insets)
            .padding(16.dp),
    ) {
        Text("Delete")
    }
}
