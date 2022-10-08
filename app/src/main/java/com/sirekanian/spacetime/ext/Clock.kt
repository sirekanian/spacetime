package com.sirekanian.spacetime.ext

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun currentDate(): LocalDate {
    val timeZone = TimeZone.currentSystemDefault()
    return Clock.System.now().toLocalDateTime(timeZone).date
}
