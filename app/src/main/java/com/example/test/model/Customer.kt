package com.example.test.model

data class Customer (
    override val id: Long = 0,
    var name: String,
    val phone: String
): IListable