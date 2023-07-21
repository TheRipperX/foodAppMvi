package com.example.foodappmvi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodappmvi.utils.DATABASE_VERSION

@Database(entities = [FoodEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class FoodDataBase: RoomDatabase() {
    abstract fun foodDao(): FoodDao
}