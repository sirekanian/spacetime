package com.sirekanian.spacetime.ext

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import kotlin.time.Clock

fun currentDate(): LocalDate =
    Clock.System.todayIn(TimeZone.currentSystemDefault())

fun LocalDate.minusDays(n: Int): LocalDate =
    minus(n, DateTimeUnit.DAY)

fun LocalDate.minusMonths(n: Int): LocalDate =
    minus(n, DateTimeUnit.MONTH)

fun LocalDate.withDayOfMonth(n: Int): LocalDate =
    if (day == n) this else LocalDate(year, month.number, n)
