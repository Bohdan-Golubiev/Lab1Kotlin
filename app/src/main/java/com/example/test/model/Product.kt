package com.example.test.model

data class Product (
    override val id: Long = 0,
    var title: String,
    val price: Double,
    val customer: Customer
): IListable