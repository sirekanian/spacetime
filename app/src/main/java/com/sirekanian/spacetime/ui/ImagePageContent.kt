package com.sirekanian.spacetime.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sirekanian.spacetime.R
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
    var isEditMode by remember { mutableStateOf(false) }
    var name by remember(isEditMode) { mutableStateOf(page.name) }
    var blur by remember(isEditMode) { mutableStateOf(page.blur) }
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
                if (name.isBlank()) {
                    TextButton(onClick = { onDelete() }) {
                        Text("DELETE", color = MaterialTheme.colors.error)
                    }
                }
                VectorIconButton(Icons.Default.Done, onClick = {
                    onDone(ImagePage(page.id, name, page.url, page.date, blur))
                    isEditMode = false
                })
            }
            Slider(value = blur, onValueChange = { blur = it }, modifier = Modifier.padding(16.dp))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        val textStyle = MaterialTheme.typography.h2.copy(textAlign = TextAlign.Center)
        if (isEditMode) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = textStyle,
                maxLines = 2,
            )
        } else {
            Text(
                text = page.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = textStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }
        page.date.getRelativeDays()?.let { days ->
            Text(
                text = when {
                    days == 0 -> "today"
                    days == 1 -> "tomorrow"
                    days == -1 -> "yesterday"
                    days > 0 -> "in $days days"
                    else -> {
                        @OptIn(ExperimentalComposeUiApi::class)
                        pluralStringResource(R.plurals.duration_days, -days, -days)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = textStyle,
            )
        }
    }
}
