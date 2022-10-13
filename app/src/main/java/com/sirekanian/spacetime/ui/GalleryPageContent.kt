package com.sirekanian.spacetime.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sirekanian.spacetime.model.Thumbnail

@Composable
fun GalleryPageContent(
    insets: PaddingValues,
    thumbnails: List<Thumbnail>,
    onSelect: (String) -> Unit,
    onEnd: () -> Unit,
) {
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
                    .clickable { onSelect(url) },
                contentScale = ContentScale.Crop,
            )
        }
        item {
            LaunchedEffect(Unit) {
                onEnd()
            }
        }
    }
}
