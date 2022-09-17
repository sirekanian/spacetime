package com.sirekanian.spacetime.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sirekanian.spacetime.ImagePage
import com.sirekanian.spacetime.MainState
import com.sirekanian.spacetime.ext.DefaultAnimatedVisibility
import com.sirekanian.spacetime.ext.VectorIconButton

@Composable
fun ImagePageContent(insets: PaddingValues, state: MainState, page: ImagePage, index: Int) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(page.name)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
    var isEditMode by remember { mutableStateOf(false) }
    DefaultAnimatedVisibility(visible = !isEditMode) {
        Row(Modifier.padding(insets)) {
            Spacer(Modifier.weight(1f))
            VectorIconButton(Icons.Default.Edit, onClick = { isEditMode = true })
        }
    }
    DefaultAnimatedVisibility(visible = isEditMode) {
        Row(Modifier.padding(insets)) {
            VectorIconButton(Icons.Default.ArrowBack, onClick = { isEditMode = false })
            Spacer(Modifier.weight(1f))
            VectorIconButton(Icons.Default.Delete, onClick = { state.removePage(index) })
        }
    }
}
