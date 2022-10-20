package com.sirekanian.spacetime.model

class EditablePage(val page: ImagePage, val autofocus: Autofocus) {
    enum class Autofocus { NAME, DATE }
}