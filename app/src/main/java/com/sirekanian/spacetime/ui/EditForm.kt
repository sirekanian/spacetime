package com.sirekanian.spacetime.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentColor
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.sirekanian.spacetime.model.EditablePage.Autofocus

@Composable
fun EditForm(
    name: NameField,
    isNameValid: Boolean,
    date: DateFieldWrapper,
    isDateValid: Boolean,
    autofocus: Autofocus?,
    textStyle: TextStyle,
) {
    val backgroundColor = LocalContentColor.current.copy(alpha = 0.1f)
    val nameFocusRequester = remember { FocusRequester() }
    val dateFocusRequester = remember { FocusRequester() }
    OutlinedTextField(
        value = name.field,
        onValueChange = name::onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(nameFocusRequester),
        textStyle = textStyle,
        placeholder = { Text("Title", Modifier.fillMaxWidth(), style = textStyle) },
        isError = !isNameValid,
        keyboardOptions = KeyboardOptions(KeyboardCapitalization.Sentences),
        maxLines = 2,
        colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = backgroundColor),
    )
    OutlinedTextField(
        value = date.field,
        onValueChange = date::onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(dateFocusRequester),
        textStyle = textStyle,
        placeholder = { Text("YYYY-MM-DD", Modifier.fillMaxWidth(), style = textStyle) },
        isError = !isDateValid,
        visualTransformation = { DateField(it.text).getVisualTransformation() },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = backgroundColor),
    )
    LaunchedEffect(Unit) {
        when (autofocus) {
            Autofocus.NAME -> nameFocusRequester
            Autofocus.DATE -> dateFocusRequester
            null -> null
        }?.requestFocus()
    }
}
