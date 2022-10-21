package com.sirekanian.spacetime.data

import android.content.res.Resources
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sirekanian.spacetime.R
import com.sirekanian.spacetime.model.ImagePage
import com.sirekanian.spacetime.model.createImagePage
import com.sirekanian.spacetime.ui.DateField

private val defaultImageUrls = listOf(
    "https://sirekanian.com/apod/image/2209/SnakingFilament_Friedman_960.jpg",
    "https://sirekanian.com/apod/image/2208/CygnusWall_Bogaerts_960.jpg",
    "https://sirekanian.com/apod/image/2209/NGC7000_NB_2022_1024.jpg",
)

class DefaultDataCallback(resources: Resources) : RoomDatabase.Callback() {

    private val defaultPages: List<ImagePage>

    init {
        val names = resources.getStringArray(R.array.default_page_names)
        val dates = resources.getStringArray(R.array.default_page_dates)
        defaultPages = names.zip(defaultImageUrls).zip(dates) { (name, url), date ->
            createImagePage(name, url, DateField(date), blur = 0.5f)
        }
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        defaultPages.forEach { page ->
            val args = arrayOf(page.name, page.url, page.date.value, page.blur)
            db.execSQL("INSERT INTO PageEntity (name, url, date, blur) VALUES (?, ?, ?, ?)", args)
        }
    }

}