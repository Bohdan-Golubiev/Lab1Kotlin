package com.example.test.repository

import com.example.test.model.Customer
import com.example.test.model.IListable
import com.example.test.model.Product

interface AppRepository {
    suspend fun getAllData(): List<IListable>

    suspend fun getAllCustomers(): List<Customer>
    suspend fun getCustomerById(id: Int): Customer
    suspend fun insertAllCustomers(customers: List<Customer>)
    suspend fun insertCustomer(customer: Customer)
    suspend fun deleteCustomer(customer: Customer)
    suspend fun deleteAllCustomers()
    suspend fun updateCustomer(customer: Customer)

    suspend fun getAllProducts(): List<Product>
    suspend fun getProductById(productId: Long): Product
    suspend fun getProductsByCustomerId(customerId: Long): List<Product>
    suspend fun insertAllProducts(products: List<Product>)
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteAllProducts()
    suspend fun updateProduct(product: Product)

    suspend fun populateTestData()

    suspend fun fetchAndSaveDataFromApi(): Result<Unit>
    suspend fun syncWithApi(): Result<String>
}