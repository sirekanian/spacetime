package com.sirekanian.spacetime.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PageDao {

    @Query("SELECT * FROM PageEntity")
    fun observe(): Flow<List<PageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(page: PageEntity)

    @Delete
    suspend fun delete(page: PageEntity)

}