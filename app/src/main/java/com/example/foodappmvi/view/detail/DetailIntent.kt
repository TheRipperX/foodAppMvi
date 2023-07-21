package com.example.foodappmvi.view.detail

import com.example.foodappmvi.data.db.FoodEntity

sealed class DetailIntent {
    data class SetDetailIntent(val id: Int): DetailIntent()

    data class CheckFoodIntent(val id: Int): DetailIntent()
    data class InsertFoodIntent(val foodEntity: FoodEntity): DetailIntent()
    data class DeleteFoodIntent(val foodEntity: FoodEntity): DetailIntent()
}
