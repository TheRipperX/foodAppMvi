package com.example.foodappmvi.data.repository

import com.example.foodappmvi.data.server.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiServices: ApiServices) {

    suspend fun reqRandom() = apiServices.requestRandom()
    suspend fun reqCategory() = apiServices.requestCategory()
    suspend fun reqFoodList(food: String) = apiServices.requestFoodList(str = food)
    suspend fun reqSearchFood(search: String) = apiServices.requestSearchFood(str = search)
}