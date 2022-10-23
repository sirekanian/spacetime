package com.sirekanian.spacetime.data

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sirekanian.spacetime.BuildConfig
import com.sirekanian.spacetime.ext.currentDate
import com.sirekanian.spacetime.model.createImagePage
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

class DebugDataCallback : RoomDatabase.Callback() {

    init {
        require(BuildConfig.DEBUG) {
            error("Not allowed in production builds")
        }
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        val current = currentDate()
        listOf(
            current.plus(-1, DateTimeUnit.DAY) to "yesterday",
            current.plus(+0, DateTimeUnit.DAY) to "today",
            current.plus(+1, DateTimeUnit.DAY) to "tomorrow",
            current.plus(-1, DateTimeUnit.MONTH) to "1 month",
            current.plus(+1, DateTimeUnit.MONTH) to "in 1 month",
        ).forEach { (date, name) ->
            val page = createImagePage(name, "", date.toString(), 0f)
            val args = arrayOf(page.name, page.url, page.date, page.blur)
            db.execSQL("INSERT INTO PageEntity (name, url, date, blur) VALUES (?, ?, ?, ?)", args)
        }
    }

}