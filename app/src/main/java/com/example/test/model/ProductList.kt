package com.example.test.model

class ProductList (
    val productList: List<Product>, override val id: Long = 0
) : IListable