package com.sirekanian.spacetime.model

import com.sirekanian.spacetime.data.local.PageEntity
import com.sirekanian.spacetime.ui.DateField

fun createImagePage(name: String, url: String, date: DateField): ImagePage =
    ImagePage(0, name, url, date)

sealed class Page(val id: Int)

class ImagePage(id: Int, val name: String, val url: String, val date: DateField) : Page(id) {
    fun toEntity() = PageEntity(id, name, url, date.value)
}

object GalleryPage : Page(-1)
