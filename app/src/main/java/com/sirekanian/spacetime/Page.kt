package com.sirekanian.spacetime

sealed class Page

class ImagePage(val name: String) : Page()

object GalleryPage : Page()
