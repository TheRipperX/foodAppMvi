package com.example.foodappmvi.utils

import com.example.foodappmvi.data.server.ApiServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServerModule {

    @Provides
    @Singleton
    fun proBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun proTimeOut() = TIME_OUT

    @Provides
    @Singleton
    fun proGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    @Named(Body)
    fun proBody() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @Named(Header)
    fun proHeader() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    @Provides
    @Singleton
    fun proClient(
        time: Long,
        @Named(Body) body: HttpLoggingInterceptor,
        @Named(Header) header: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .readTimeout(time, TimeUnit.SECONDS)
        .writeTimeout(time, TimeUnit.SECONDS)
        .connectTimeout(time, TimeUnit.SECONDS)
        .addInterceptor(body)
        .addInterceptor(header)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun proRetrofit(url: String, gson: Gson, client: OkHttpClient): ApiServices = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
        .create(ApiServices::class.java)


}