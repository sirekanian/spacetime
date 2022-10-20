package com.sirekanian.spacetime.ui

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    val name = remember(isEditMode) { NameField(page.name) }
    var date by remember(isEditMode) { mutableStateOf(page.date) }
    var blur by remember(isEditMode) { mutableStateOf(page.blur) }
    var isDateValid by remember(date) { mutableStateOf(true) }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(page.url)
            .crossfade(true)
            .let {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    val context = LocalContext.current
                    val blurTransformation = remember(blur) {
                        BlurTransformation(context, blur * 22 + 3, page.url)
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
            .let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    it.blur((blur * 22 + 3).dp)
                } else {
                    it
                }
            },
        contentScale = ContentScale.Crop,
    )
    DefaultAnimatedVisibility(visible = isEditMode) {
        Row(Modifier.padding(insets)) {
            VectorIconButton(Icons.Default.Close, onClick = { state.editablePage = null })
            Spacer(Modifier.weight(1f))
            if (name.field.text.isEmpty() && date.value.isEmpty()) {
                TextButton(onClick = { onDelete() }) {
                    Text("DELETE", color = MaterialTheme.colors.error)
                }
            } else {
                VectorIconButton(Icons.Default.Done, onClick = {
                    if (date.isValid()) {
                        onDone(ImagePage(page.id, name.field.text, page.url, date, blur))
                    } else {
                        isDateValid = false
                    }
                })
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(top = 48.dp) // TODO 1203173026241869: remove hardcoded toolbar size
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val topSpaceWeight by animateFloatAsState(if (isEditMode) 0f else 1f)
        topSpaceWeight.let { weight -> if (weight > 0) Spacer(Modifier.weight(weight)) }
        val textStyle = MaterialTheme.typography.h2.copy(textAlign = TextAlign.Center)
        if (isEditMode) {
            val ripple = LocalRippleTheme.current
            val rippleAlpha = ripple.rippleAlpha().pressedAlpha
            val rippleColor = ripple.defaultColor().copy(alpha = rippleAlpha)
            val nameFocusRequester = remember { FocusRequester() }
            val dateFocusRequester = remember { FocusRequester() }
            OutlinedTextField(
                value = name.field,
                onValueChange = { name.field = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(nameFocusRequester),
                textStyle = textStyle,
                placeholder = { Text("Title", Modifier.fillMaxWidth(), style = textStyle) },
                maxLines = 2,
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = rippleColor),
            )
            OutlinedTextField(
                value = date.value,
                onValueChange = { date = DateField(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(dateFocusRequester),
                textStyle = textStyle,
                placeholder = { Text("YYYY-MM-DD", Modifier.fillMaxWidth(), style = textStyle) },
                isError = !isDateValid,
                visualTransformation = { DateField(it.text).getVisualTransformation() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = rippleColor),
            )
            LaunchedEffect(Unit) {
                when (state.editablePage?.autofocus) {
                    Autofocus.NAME -> nameFocusRequester
                    Autofocus.DATE -> dateFocusRequester
                    null -> null
                }?.requestFocus()
            }
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
            page.date.getRelativeDays()?.let { days ->
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
