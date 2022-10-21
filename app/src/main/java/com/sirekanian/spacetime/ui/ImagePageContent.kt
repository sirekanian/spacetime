package com.sirekanian.spacetime.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sirekanian.spacetime.D
import com.sirekanian.spacetime.MainState
import com.sirekanian.spacetime.R
import com.sirekanian.spacetime.ext.DefaultAnimatedVisibility
import com.sirekanian.spacetime.ext.VectorIconButton
import com.sirekanian.spacetime.model.EditablePage
import com.sirekanian.spacetime.model.EditablePage.Autofocus
import com.sirekanian.spacetime.model.ImagePage

@Composable
fun ImagePageContent(
    insets: PaddingValues,
    state: MainState,
    page: ImagePage,
    onDelete: () -> Unit,
    onDone: (ImagePage) -> Unit,
) {
    val isEditMode = state.editablePage?.page?.id == page.id
    BackHandler(isEditMode) {
        state.editablePage = null
    }
    val name = remember { NameField(page.name) }
    val date = remember { DateFieldWrapper(page.date) }
    var blur by remember(isEditMode) { mutableStateOf(page.blur) }
    var isNameValid by remember(name.field) { mutableStateOf(true) }
    var isDateValid by remember(date.field) { mutableStateOf(true) }
    PageBackground(
        url = page.url,
        blur = blur,
    )
    DefaultAnimatedVisibility(visible = isEditMode) {
        Row(Modifier.padding(insets)) {
            VectorIconButton(Icons.Default.Close, onClick = { state.editablePage = null })
            Spacer(Modifier.weight(1f))
            if (name.isEmpty() && date.isEmpty()) {
                TextButton(onClick = { onDelete() }) {
                    Text("DELETE", color = MaterialTheme.colors.error)
                }
            } else {
                VectorIconButton(Icons.Default.Done, onClick = {
                    isNameValid = name.isValid()
                    isDateValid = date.isValid()
                    if (isNameValid && isDateValid) {
                        onDone(ImagePage(page.id, name.field.text, page.url, date.field.text, blur))
                    }
                })
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(top = D.toolbarHeight)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val topSpaceWeight by animateFloatAsState(if (isEditMode) 0f else 1f)
        topSpaceWeight.let { weight -> if (weight > 0) Spacer(Modifier.weight(weight)) }
        val textStyle = MaterialTheme.typography.h2.copy(textAlign = TextAlign.Center)
        if (isEditMode) {
            EditForm(
                name = name,
                isNameValid = isNameValid,
                date = date,
                isDateValid = isDateValid,
                autofocus = state.editablePage?.autofocus,
                textStyle = textStyle,
            )
        } else {
            val haptic = LocalHapticFeedback.current
            Text(
                text = page.name,
                modifier = @OptIn(ExperimentalFoundationApi::class) Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .combinedClickable(onLongClick = {
                        state.editablePage = EditablePage(page, Autofocus.NAME)
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }) {}
                    .padding(16.dp),
                style = textStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
            DateField(page.date).getRelativeDays()?.let { days ->
                Text(
                    text = @OptIn(ExperimentalComposeUiApi::class) when {
                        days == 0 -> stringResource(R.string.duration_today)
                        days == 1 -> stringResource(R.string.duration_tomorrow)
                        days == -1 -> stringResource(R.string.duration_yesterday)
                        days > 0 -> pluralStringResource(R.plurals.duration_in_days, days, days)
                        else -> pluralStringResource(R.plurals.duration_days, -days, -days)
                    },
                    modifier = @OptIn(ExperimentalFoundationApi::class) Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .combinedClickable(onLongClick = {
                            state.editablePage = EditablePage(page, Autofocus.DATE)
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }) {}
                        .padding(16.dp),
                    style = textStyle,
                )
            }
        }
        Box(Modifier.height(48.dp), Alignment.Center) {
            DefaultAnimatedVisibility(visible = isEditMode) {
                Slider(
                    value = blur,
                    onValueChange = { blur = it },
                )
            }
        }
        Spacer(Modifier.weight(1f))
    }
}
