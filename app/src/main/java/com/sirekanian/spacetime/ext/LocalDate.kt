package com.sirekanian.spacetime.ext

import kotlinx.datetime.*

fun currentDate(): LocalDate =
    Clock.System.todayIn(TimeZone.currentSystemDefault())

fun LocalDate.minusDays(n: Int): LocalDate =
    minus(n, DateTimeUnit.DAY)

fun LocalDate.minusMonths(n: Int): LocalDate =
    minus(n, DateTimeUnit.MONTH)

fun LocalDate.withDayOfMonth(n: Int): LocalDate =
    if (dayOfMonth == n) this else LocalDate(year, monthNumber, n)
