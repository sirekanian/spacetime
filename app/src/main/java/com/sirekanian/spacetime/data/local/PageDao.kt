package com.sirekanian.spacetime.data.local

import androidx.room.*

@Dao
interface PageDao {

    @Query("SELECT * FROM PageEntity")
    suspend fun select(): List<PageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(page: PageEntity)

    @Delete
    suspend fun delete(page: PageEntity)

}