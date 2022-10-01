package com.sirekanian.spacetime.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PageEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun getPageDao(): PageDao

}