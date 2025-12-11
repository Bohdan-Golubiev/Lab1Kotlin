package com.example.test.model

class CustomerList (
    val customerList: List<Customer>, override val id: Long = 0
) : IListable