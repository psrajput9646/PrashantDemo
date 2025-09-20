package com.example.stocks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InvestedDao {
    @Query("SELECT * FROM invested")
    fun getInvestedList(): Flow<List<UserHoldingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<UserHoldingEntity>)

    // Delete all
    @Query("DELETE FROM invested")
    suspend fun deleteAll()
}