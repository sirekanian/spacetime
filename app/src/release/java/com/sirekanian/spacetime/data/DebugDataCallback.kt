package com.sirekanian.spacetime.data

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class DebugDataCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        // noop
    }
}