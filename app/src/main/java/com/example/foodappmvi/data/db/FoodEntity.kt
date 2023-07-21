package com.example.foodappmvi.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodappmvi.utils.FOOD_TABLE

@Entity(tableName = FOOD_TABLE)
data class FoodEntity(
    @PrimaryKey
    var id: Int = 0,
    var foodName: String = "",
    var foodImage: String = ""
)