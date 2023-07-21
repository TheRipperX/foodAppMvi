package com.example.foodappmvi.data.repository

import com.example.foodappmvi.data.db.FoodDao
import javax.inject.Inject

class FavRepository @Inject constructor(private val dao: FoodDao) {

    fun showAllFood() = dao.allFoods()
}