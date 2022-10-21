package com.sirekanian.spacetime.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DateFieldWrapper(date: String) {
    var field by mutableStateOf(DateField(date))
    fun isEmpty() = field.value.isEmpty()
    fun isValid() = field.isValid()
}