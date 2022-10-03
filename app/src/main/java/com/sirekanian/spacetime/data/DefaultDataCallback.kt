package com.sirekanian.spacetime.data

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class DefaultDataCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        URLS.take(2).forEach {
            db.execSQL("INSERT INTO PageEntity (name, url) VALUES (?, ?)", arrayOf("", it))
        }
    }
}