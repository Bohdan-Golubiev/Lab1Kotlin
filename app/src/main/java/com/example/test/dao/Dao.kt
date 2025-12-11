package com.example.test.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.test.entity.CustomerEntity
import com.example.test.entity.ProductEntity

@Dao
interface Dao {
   //Customer DAO
   @Query("SELECT * FROM customers")
   suspend fun getAllCustomers(): List<CustomerEntity>

   @Query("SELECT * FROM customers WHERE customer_id = :id")
   suspend fun getCustomerById(id: Int): CustomerEntity

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAllCustomers(customers: List<CustomerEntity>): List<Long>

   @Insert
   suspend fun insertCustomer(customers: CustomerEntity)

   @Delete
   suspend fun deleteCustomer(customers: CustomerEntity)

   @Query("DELETE FROM customers")
   suspend fun deleteAllCustomers()

   @Update
   suspend fun updateCustomer(customer: CustomerEntity)

   //Product DAO
   @Query("SELECT * FROM products")
   suspend fun getAllProducts(): List<ProductEntity>

   @Query("SELECT * FROM products WHERE product_id = :id")
   suspend fun getProductById(id: Long): ProductEntity

   @Query("SELECT * FROM products WHERE customer_id = :customerId")
   suspend fun getProductsByCustomerId(customerId: Long): List<ProductEntity>

   @Insert
   suspend fun insertAllProducts(vararg products: ProductEntity)

   @Insert
   suspend fun insertProduct(product: ProductEntity)

   @Delete
   suspend fun deleteProduct(product: ProductEntity)

   @Query("DELETE FROM products")
   suspend fun deleteAllProducts()

   @Transaction
   @Update
   suspend fun updateProduct(book: ProductEntity)
}