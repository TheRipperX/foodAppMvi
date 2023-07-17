package com.example.foodappmvi.data.repository

import com.example.foodappmvi.data.server.ApiServices
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiServices: ApiServices) {

    suspend fun reqDetail(id: Int) = apiServices.requestDetail(id)
}