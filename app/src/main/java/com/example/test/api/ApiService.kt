package com.example.test.api

import com.example.test.dto.CustomerDto
import com.example.test.dto.ProductDto
import retrofit2.http.GET

interface ApiService {

    @GET("Customers")
    suspend fun getCustomers(): List<CustomerDto>

    @GET("Products")
    suspend fun getProducts(): List<ProductDto>
}