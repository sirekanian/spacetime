package com.sirekanian.spacetime.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sirekanian.spacetime.ext.DefaultAnimatedVisibility
import com.sirekanian.spacetime.ext.VectorIconButton
import com.sirekanian.spacetime.model.ImagePage

@Composable
fun ImagePageContent(
    insets: PaddingValues,
    page: ImagePage,
    onDelete: () -> Unit,
    onDone: (ImagePage) -> Unit,
) {
    var blur by remember { mutableStateOf(page.blur) }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(page.url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .blur((blur * 32).dp),
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
        Column(
            modifier = Modifier
                .padding(insets)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row {
                VectorIconButton(Icons.Default.ArrowBack, onClick = { isEditMode = false })
                Spacer(Modifier.weight(1f))
                VectorIconButton(Icons.Default.Delete, onClick = { onDelete() })
                VectorIconButton(Icons.Default.Done, onClick = {
                    onDone(ImagePage(page.id, page.name, page.url, page.date, blur))
                    isEditMode = false
                })
            }
            Slider(value = blur, onValueChange = { blur = it }, modifier = Modifier.padding(16.dp))
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = page.name,
            style = MaterialTheme.typography.h2,
        )
        page.date.getRelativeDays()?.let { days ->
            Text(
                text = when {
                    days == 0 -> "today"
                    days == 1 -> "tomorrow"
                    days == -1 -> "yesterday"
                    days > 0 -> "in $days days"
                    else -> "${-days} days"
                },
                style = MaterialTheme.typography.h3,
            )
        }
    }
}
