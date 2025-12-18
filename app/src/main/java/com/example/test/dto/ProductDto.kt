package com.example.test.dto

import com.google.gson.annotations.SerializedName

data class ProductDto (
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("customerId")
    val customerId: String
)