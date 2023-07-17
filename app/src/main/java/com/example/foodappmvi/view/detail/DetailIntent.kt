package com.example.foodappmvi.view.detail

sealed class DetailIntent {
    data class SetDetailIntent(val id: Int): DetailIntent()
}
