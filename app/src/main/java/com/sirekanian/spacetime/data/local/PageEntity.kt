package com.sirekanian.spacetime.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sirekanian.spacetime.model.ImagePage
import com.sirekanian.spacetime.ui.DateField

@Entity
class PageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val url: String,
    val date: String,
) {
    fun toModel() = ImagePage(id, name, url, DateField(date))
}