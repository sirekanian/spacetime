package com.sirekanian.spacetime.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

class DateFieldWrapper(date: String) {

    private val text = DateField(date).value
    var field by mutableStateOf(TextFieldValue(text, TextRange(text.length)))
        private set

    fun onValueChange(field: TextFieldValue) {
        this.field = TextFieldValue(DateField(field.text).value, field.selection)
    }

    fun isEmpty(): Boolean =
        field.text.isEmpty()

    fun isValid(): Boolean =
        DateField(field.text).isValid()

}