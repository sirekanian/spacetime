package com.sirekanian.spacetime

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sirekanian.spacetime.ext.currentDate
import com.sirekanian.spacetime.ext.minusMonths
import com.sirekanian.spacetime.ext.withDayOfMonth
import com.sirekanian.spacetime.model.Draft
import com.sirekanian.spacetime.model.Thumbnail
import kotlinx.datetime.LocalDate

class MainState {

    var draft by mutableStateOf<Draft?>(null)
    var thumbnails by mutableStateOf(listOf<Thumbnail>())
    val nextDate: LocalDate by derivedStateOf {
        thumbnails.let {
            if (it.isEmpty()) {
                currentDate().withDayOfMonth(1)
            } else {
                it.minOf(Thumbnail::date).withDayOfMonth(1).minusMonths(1)
            }
        }
    }

}