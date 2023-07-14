package com.example.foodappmvi.data.model.server


import com.google.gson.annotations.SerializedName

data class ResponseCategory(
    @SerializedName("categories")
    var categories: List<Category>
) {
    data class Category(
        @SerializedName("idCategory")
        var idCategory: String,
        @SerializedName("strCategory")
        var strCategory: String,
        @SerializedName("strCategoryDescription")
        var strCategoryDescription: String,
        @SerializedName("strCategoryThumb")
        var strCategoryThumb: String
    )
}