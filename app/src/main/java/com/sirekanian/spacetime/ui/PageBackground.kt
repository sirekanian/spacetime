package com.sirekanian.spacetime.ui

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun PageBackground(url: String, blur: Float) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .let {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    val context = LocalContext.current
                    val blurTransformation = remember(blur) {
                        BlurTransformation(context, blur * 22 + 3, url)
                    }
                    it.transformations(blurTransformation)
                } else {
                    it
                }
            }
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = false, onClick = {})
            .let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    it.blur((blur * 22 + 3).dp)
                } else {
                    it
                }
            },
        contentScale = ContentScale.Crop,
    )
}
