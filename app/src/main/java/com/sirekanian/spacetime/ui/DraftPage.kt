package com.sirekanian.spacetime.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sirekanian.spacetime.D
import com.sirekanian.spacetime.ext.VectorIconButton
import com.sirekanian.spacetime.model.EditablePage
import com.sirekanian.spacetime.model.ImagePage
import com.sirekanian.spacetime.model.createImagePage

@Composable
fun DraftPage(
    insets: PaddingValues,
    editablePage: EditablePage,
    onClose: () -> Unit,
    onDone: (ImagePage) -> Unit,
) {
    val draft = editablePage.page
    BackHandler {
        onClose()
    }
    val name = remember { NameField(draft.name) }
    val date = remember { DateFieldWrapper(draft.date) }
    var blur by remember { mutableStateOf(draft.blur) }
    var isNameValid by remember(name.field) { mutableStateOf(true) }
    var isDateValid by remember(date.field) { mutableStateOf(true) }
    PageBackground(
        url = draft.url,
        blur = blur,
    )
    Row(Modifier.padding(insets)) {
        VectorIconButton(Icons.Default.Close, onClick = onClose)
        Spacer(Modifier.weight(1f))
        VectorIconButton(Icons.Default.Done, onClick = {
            isNameValid = name.isValid()
            isDateValid = date.isValid()
            if (isNameValid && isDateValid) {
                onDone(createImagePage(name.field.text, draft.url, date.field.text, blur))
            }
        })
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(top = D.toolbarHeight)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val textStyle = MaterialTheme.typography.h2.copy(textAlign = TextAlign.Center)
        EditForm(
            name = name,
            isNameValid = isNameValid,
            date = date,
            isDateValid = isDateValid,
            autofocus = editablePage.autofocus,
            textStyle = textStyle,
        )
        Box(Modifier.height(48.dp), Alignment.Center) {
            Slider(
                value = blur,
                onValueChange = { blur = it },
            )
        }
        Spacer(Modifier.weight(1f))
    }
}
