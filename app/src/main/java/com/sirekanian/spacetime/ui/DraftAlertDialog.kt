package com.sirekanian.spacetime.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sirekanian.spacetime.MainState
import com.sirekanian.spacetime.model.ImagePage

@Composable
fun DraftAlertDialog(state: MainState, onConfirm: (ImagePage) -> Unit) {
    val draft = state.draft
    var name by remember(draft) { mutableStateOf("") }
    var date by remember(draft) { mutableStateOf("") }
    if (draft != null) {
        MyAlertDialog(
            title = {
                Text(
                    text = "Enter Name",
                    style = MaterialTheme.typography.h6,
                )
            },
            content = {
                val focusRequester = remember { FocusRequester() }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.focusRequester(focusRequester),
                    label = { Text("Name") },
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") },
                    placeholder = { Text("YYYY-MM-DD") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { state.draft = null },
                    content = { Text("CANCEL") },
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm(ImagePage(0, name, draft.url, date))
                        state.draft = null
                    },
                    content = { Text("OK") },
                )
            },
            onDismiss = {
                state.draft = null
            },
        )
    }
}

@Composable
private fun MyAlertDialog(
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column {
                Column(Modifier.padding(24.dp)) {
                    title.invoke()
                    Spacer(Modifier.size(16.dp))
                    content.invoke()
                }
                Spacer(Modifier.size(4.dp))
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    dismissButton.invoke()
                    confirmButton.invoke()
                }
            }
        }
    }
}