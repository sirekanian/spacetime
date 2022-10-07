package com.sirekanian.spacetime.model

import com.sirekanian.spacetime.data.local.PageEntity

sealed class Page(val id: Int)

class ImagePage(id: Int, val name: String, val url: String, val date: String) : Page(id) {
    fun toEntity() = PageEntity(id, name, url, date)
}

object GalleryPage : Page(-1)
