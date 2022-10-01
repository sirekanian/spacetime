package com.sirekanian.spacetime.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PageDao {

    @Query("SELECT * FROM PageEntity")
    fun observe(): Flow<List<PageEntity>>

    @Insert
    suspend fun insert(page: PageEntity)

    @Delete
    suspend fun delete(page: PageEntity)

}