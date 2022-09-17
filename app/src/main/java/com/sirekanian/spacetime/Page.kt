package com.sirekanian.spacetime

import java.util.*

sealed class Page {
    val uuid: String = UUID.randomUUID().toString()
}

class ImagePage(val name: String) : Page()

object GalleryPage : Page()
