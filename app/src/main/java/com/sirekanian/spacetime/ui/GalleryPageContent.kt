package com.sirekanian.spacetime.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sirekanian.spacetime.ext.DefaultAnimatedVisibility
import com.sirekanian.spacetime.model.Thumbnail

@Composable
fun GalleryPageContent(
    insets: PaddingValues,
    thumbnails: List<Thumbnail>,
    onSelect: (String) -> Unit,
    onEnd: () -> Unit,
) {
    var previewImageUrl by remember { mutableStateOf<String?>(null) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = insets,
    ) {
        items(thumbnails.size) { index ->
            val url = thumbnails[index].url
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .combinedClickable(
                        onClick = {
                            onSelect(url)
                        },
                        onLongClick = {
                            previewImageUrl = url
                        },
                    ),
                contentScale = ContentScale.Crop,
            )
        }
        item {
            LaunchedEffect(Unit) {
                onEnd()
            }
        }
    }
    DefaultAnimatedVisibility(visible = previewImageUrl != null) {
        previewImageUrl?.let { url ->
            ImagePreview(url, onClick = { previewImageUrl = null })
        }
    }
}

@Composable
private fun ImagePreview(url: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xCC000000))
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
    )
}
