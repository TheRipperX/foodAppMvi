package com.example.foodappmvi.view.detail

import com.example.foodappmvi.data.model.server.ResponseDetail

sealed class DetailState {
    object Idle: DetailState()
    object SetDetailLoading: DetailState()
    data class Error(val error: String): DetailState()
    data class SetDetails(val list: MutableList<ResponseDetail.Meal>): DetailState()
}
