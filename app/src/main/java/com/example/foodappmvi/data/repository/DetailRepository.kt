package com.example.foodappmvi.data.repository

import com.example.foodappmvi.data.db.FoodDao
import com.example.foodappmvi.data.db.FoodEntity
import com.example.foodappmvi.data.server.ApiServices
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiServices: ApiServices, private val dao: FoodDao) {

    suspend fun reqDetail(id: Int) = apiServices.requestDetail(id)

    suspend fun insertFood(foodEntity: FoodEntity) = dao.insertFood(foodEntity)
    suspend fun deleteFood(foodEntity: FoodEntity) = dao.deleteFood(foodEntity)
    fun checkFood(id: Int) = dao.isFoodCheck(id)
}