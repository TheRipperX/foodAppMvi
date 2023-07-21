package com.example.foodappmvi.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodappmvi.utils.FOOD_TABLE

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(foodEntity: FoodEntity)

    @Delete
    suspend fun deleteFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM $FOOD_TABLE")
    fun allFoods(): MutableList<FoodEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM $FOOD_TABLE WHERE id = :id)")
    fun isFoodCheck(id: Int): Boolean

}