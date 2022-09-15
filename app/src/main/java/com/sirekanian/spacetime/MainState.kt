package com.sirekanian.spacetime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sirekanian.spacetime.data.URLS

class MainState {

    private var list by mutableStateOf(
        URLS.take(2).map(::ImagePage)
    )

    fun getPages(): List<Page> =
        list.plus(GalleryPage)

    fun addPage(name: String) {
        list = list.plus(ImagePage(name))
    }

    fun removePage(index: Int) {
        list = list.filterIndexed { i, _ -> i != index }
    }

}