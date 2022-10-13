package com.sirekanian.spacetime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sirekanian.spacetime.model.Draft
import com.sirekanian.spacetime.model.Thumbnail

class MainState {

    var draft by mutableStateOf<Draft?>(null)
    var thumbnails by mutableStateOf(listOf<Thumbnail>())

}