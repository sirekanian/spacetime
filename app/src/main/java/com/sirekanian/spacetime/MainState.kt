package com.sirekanian.spacetime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainState {

    private var list by mutableStateOf(
        listOf(
            ImagePage("default1"),
            ImagePage("default2"),
        )
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