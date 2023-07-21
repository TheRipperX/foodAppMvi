package com.example.foodappmvi.view.favorite

import com.example.foodappmvi.data.db.FoodEntity

sealed class FavState {
    object Empty: FavState()
    data class ShowAllFood(val foods: MutableList<FoodEntity>): FavState()
}
