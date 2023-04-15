package com.sirekanian.spacetime.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
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
import com.sirekanian.spacetime.R
import com.sirekanian.spacetime.ext.DefaultAnimatedVisibility
import com.sirekanian.spacetime.ext.VectorIconButton
import com.sirekanian.spacetime.ext.currentDate
import com.sirekanian.spacetime.model.EditablePage
import com.sirekanian.spacetime.model.EditablePage.Autofocus
import com.sirekanian.spacetime.model.ImagePage
import kotlinx.datetime.daysUntil
import kotlinx.datetime.periodUntil
import kotlin.math.abs

@Composable
fun ImagePageContent(
    insets: PaddingValues,
    page: ImagePage,
    editablePage: EditablePage?,
    onEdit: (EditablePage) -> Unit,
    onClose: () -> Unit,
    onDelete: () -> Unit,
    onDone: (ImagePage) -> Unit,
) {
    val isEditMode = editablePage?.page?.id == page.id
    BackHandler(isEditMode) {
        onClose()
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
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(insets),
        ) {
            VectorIconButton(Icons.Default.Close, onClick = onClose)
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
                autofocus = editablePage?.autofocus,
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
                        onEdit(EditablePage(page, Autofocus.NAME))
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }) {}
                    .padding(16.dp),
                style = textStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
            var isDefaultDateFormat by remember { mutableStateOf(true) }
            DateField(page.date).getLocalDate()?.let { date ->
                val current = remember { currentDate() }
                Text(
                    text = @OptIn(ExperimentalComposeUiApi::class) if (isDefaultDateFormat) {
                        val days = date.daysUntil(current)
                        when {
                            days == 0 -> stringResource(R.string.duration_today)
                            days == -1 -> stringResource(R.string.duration_tomorrow)
                            days == 1 -> stringResource(R.string.duration_yesterday)
                            days < 0 -> pluralStringResource(R.plurals.duration_in_days, -days, -days)
                            else -> pluralStringResource(R.plurals.duration_days, days, days)
                        }
                    } else {
                        val period = date.periodUntil(current)
                        @Suppress("SimplifiableCallChain")
                        listOf(
                            R.plurals.duration_years to period.years,
                            R.plurals.duration_months to period.months,
                            R.plurals.duration_days to period.days,
                        )
                            .filter { (_, value) -> value != 0 }
                            .ifEmpty { listOf(R.plurals.duration_days to period.days) }
                            .map { (id, value) -> pluralStringResource(id, abs(value), abs(value)) }
                            .joinToString(" ", if (date > current) "in " else "")
                    },
                    modifier = @OptIn(ExperimentalFoundationApi::class) Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                        .animateContentSize()
                        .combinedClickable(
                            onClick = {
                                isDefaultDateFormat = !isDefaultDateFormat
                            },
                            onLongClick = {
                                onEdit(EditablePage(page, Autofocus.DATE))
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                        )
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
