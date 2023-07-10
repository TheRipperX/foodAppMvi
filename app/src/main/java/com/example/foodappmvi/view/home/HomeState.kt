package com.example.foodappmvi.view.home

import com.example.foodappmvi.data.model.server.ResponseRandom

sealed class HomeState {
    object Idle: HomeState()
    data class SetSpinnerState(val list: MutableList<*>): HomeState()
    data class SetRandomState(val img: ResponseRandom.Meal?): HomeState()
    data class Error(val error: String): HomeState()
}
