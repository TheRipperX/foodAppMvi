package com.example.foodappmvi.data.server

import com.example.foodappmvi.data.model.server.ResponseRandom
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("random.php")
    suspend fun requestRandom(): Response<ResponseRandom>
}