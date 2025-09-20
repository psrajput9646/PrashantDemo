package com.example.stocks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserHoldingEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun investDao(): InvestedDao

}