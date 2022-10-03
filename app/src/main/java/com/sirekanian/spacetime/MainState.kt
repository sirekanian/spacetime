package com.sirekanian.spacetime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sirekanian.spacetime.model.Draft

class MainState {

    var draft by mutableStateOf<Draft?>(null)

}