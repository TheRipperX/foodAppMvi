package com.example.foodappmvi.view.home

import com.example.foodappmvi.data.model.server.ResponseCategory
import com.example.foodappmvi.data.model.server.ResponseFoodList
import com.example.foodappmvi.data.model.server.ResponseRandom

sealed class HomeState {
    object Idle: HomeState()
    //set item to spinner
    data class SetSpinnerState(val list: MutableList<Char>): HomeState()
    //set item to image random
    data class SetRandomState(val img: ResponseRandom.Meal?): HomeState()
    //set item to category adapter
    data class SetCategoryState(val list: MutableList<ResponseCategory.Category>): HomeState()
    object SetLoadingCategory: HomeState()
    data class SetFoodListState(val foodList: MutableList<ResponseFoodList.Meal>): HomeState()
    object SetLoadingFoodList: HomeState()
    object Empty: HomeState()
    data class Error(val error: String): HomeState()
}
