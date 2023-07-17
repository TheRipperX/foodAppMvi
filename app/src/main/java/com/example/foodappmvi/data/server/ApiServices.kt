package com.example.foodappmvi.data.server

import com.example.foodappmvi.data.model.server.ResponseCategory
import com.example.foodappmvi.data.model.server.ResponseDetail
import com.example.foodappmvi.data.model.server.ResponseFilterCategory
import com.example.foodappmvi.data.model.server.ResponseFoodList
import com.example.foodappmvi.data.model.server.ResponseRandom
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("random.php")
    suspend fun requestRandom(): Response<ResponseRandom>
    @GET("categories.php")
    suspend fun requestCategory(): Response<ResponseCategory>
    @GET("search.php")
    suspend fun requestFoodList(@Query("f") str: String): Response<ResponseFoodList>
    @GET("search.php")
    suspend fun requestSearchFood(@Query("s") str: String): Response<ResponseFoodList>
    @GET("filter.php")
    suspend fun requestFilterByCategory(@Query("c") str: String): Response<ResponseFoodList>
    @GET("lookup.php")
    suspend fun requestDetail(@Query("i") id: Int): Response<ResponseDetail>
}