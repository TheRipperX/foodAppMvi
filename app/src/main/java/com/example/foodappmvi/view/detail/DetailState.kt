package com.example.foodappmvi.view.detail

import com.example.foodappmvi.data.model.server.ResponseDetail

sealed class DetailState {
    object Idle: DetailState()
    object SetDetailLoading: DetailState()
    data class Error(val error: String): DetailState()
    data class SetDetails(val list: MutableList<ResponseDetail.Meal>): DetailState()

    data class SaveFoodState(val unit: Unit): DetailState()
    data class DeleteFoodState(val unit: Unit): DetailState()
    data class CheckFoodState(val exists: Boolean): DetailState()
}
