package com.example.foodappmvi.data.model.server


import com.google.gson.annotations.SerializedName

data class ResponseFilterCategory(
    @SerializedName("meals")
    var meals: List<Meal>
) {
    data class Meal(
        @SerializedName("idMeal")
        var idMeal: String,
        @SerializedName("strMeal")
        var strMeal: String,
        @SerializedName("strMealThumb")
        var strMealThumb: String
    )
}