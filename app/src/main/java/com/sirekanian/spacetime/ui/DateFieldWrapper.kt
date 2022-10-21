package com.sirekanian.spacetime.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DateFieldWrapper(date: DateField) {
    var field by mutableStateOf(date)
    fun isEmpty() = field.value.isEmpty()
    fun isValid() = field.isValid()
}