package com.sirekanian.spacetime

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sirekanian.spacetime.ext.currentDate
import com.sirekanian.spacetime.ext.minusDays
import com.sirekanian.spacetime.ext.minusMonths
import com.sirekanian.spacetime.ext.withDayOfMonth
import com.sirekanian.spacetime.model.EditablePage
import com.sirekanian.spacetime.model.Page
import com.sirekanian.spacetime.model.Thumbnail
import kotlinx.datetime.LocalDate

class MainState {

    val pagerState = PagerState { pages.size }
    var editablePage by mutableStateOf<EditablePage?>(null)
    var pages by mutableStateOf(listOf<Page>())
    val draft by derivedStateOf { editablePage?.takeIf { it.page.id == 0 } }
    var thumbnails by mutableStateOf<List<Thumbnail>?>(listOf())
    val nextDate: LocalDate by derivedStateOf {
        thumbnails.let {
            if (it.isNullOrEmpty()) {
                currentDate().minusDays(2).withDayOfMonth(1)
            } else {
                it.minOf(Thumbnail::date).withDayOfMonth(1).minusMonths(1)
            }
        }
    }

}