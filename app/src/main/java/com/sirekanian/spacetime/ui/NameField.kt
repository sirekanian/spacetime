package com.sirekanian.spacetime.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

class NameField(name: String) {
    var field by mutableStateOf(TextFieldValue(name, TextRange(name.length)))
    fun isEmpty() = field.text.isEmpty()
    fun isValid() = field.text.isNotEmpty()
}