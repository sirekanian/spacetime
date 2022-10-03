package com.sirekanian.spacetime.model

import com.sirekanian.spacetime.data.local.PageEntity

sealed class Page(val id: Int)

class ImagePage(id: Int, val name: String, val url: String) : Page(id) {
    fun toEntity() = PageEntity(id, name, url)
}

object GalleryPage : Page(-1)
