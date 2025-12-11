package com.example.test.utils

import com.example.test.entity.CustomerEntity
import com.example.test.entity.ProductEntity
import com.example.test.model.Customer
import com.example.test.model.Product

fun CustomerEntity.toDomain() = Customer(
    id = this.id,
    name = this.name,
    phone = this.phone
)

fun ProductEntity.toDomain(customer: Customer) = Product(
    id = this.id,
    title = this.title,
    price = this.price,
    customer = customer
)

fun Customer.toEntity() = CustomerEntity(
    id = this.id,
    name = this.name,
    phone = this.phone
)

fun Product.toEntity() = ProductEntity(
    id = this.id,
    title = this.title,
    price = this.price,
    customerId = this.customer.id
)