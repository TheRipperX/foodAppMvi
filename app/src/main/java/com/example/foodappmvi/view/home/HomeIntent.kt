package com.example.foodappmvi.view.home

sealed class HomeIntent {
    object SetSpinnerIntent: HomeIntent()
    object SetRandomIntent: HomeIntent()
}
