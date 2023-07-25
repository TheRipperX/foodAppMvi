package com.example.foodappmvi.utils.network

import kotlinx.coroutines.flow.Flow

interface NetworkCheck {

    enum class Status{ Available, Unavailable, Losing, Lost }

    fun observeNet(): Flow<Status>
}