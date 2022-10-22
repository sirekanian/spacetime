package com.sirekanian.spacetime.ui

import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import kotlinx.datetime.LocalDate

private val offsetMapping = object : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int =
        when (offset) {
            in (0..3) -> offset
            in (4..5) -> offset + 1
            else -> offset + 2
        }

    override fun transformedToOriginal(offset: Int): Int =
        when (offset) {
            in (0..4) -> offset
            in (5..7) -> offset - 1
            else -> offset - 2
        }
}

class DateField(value: String) {

    val value = value.filter(Char::isDigit).take(8)
    private val year: String get() = value.take(4)
    private val month: String get() = value.drop(4).take(2)
    private val day: String get() = value.drop(6).take(2)

    fun isValid(): Boolean =
        getLocalDate() != null

    fun getLocalDate(): LocalDate? {
        if (value.length == 8) {
            try {
                return LocalDate(year.toInt(), month.toInt(), day.toInt())
            } catch (exception: IllegalArgumentException) {
                Log.d("SPACETIME", "Cannot parse date", exception)
            }
        }
        return null
    }

    fun getVisualTransformation(): TransformedText =
        TransformedText(AnnotatedString(getFormattedValue()), offsetMapping)

    private fun getFormattedValue(): String =
        when (value.length) {
            in (0..3) -> year
            in (4..5) -> "$year-$month"
            else -> "$year-$month-$day"
        }

}