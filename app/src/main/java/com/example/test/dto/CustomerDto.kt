package com.example.test.dto
import com.google.gson.annotations.SerializedName


data class CustomerDto (
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone")
    val phone: String
)