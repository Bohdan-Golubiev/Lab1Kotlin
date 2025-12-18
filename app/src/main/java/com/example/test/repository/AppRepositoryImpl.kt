package com.example.test.repository

import android.util.Log
import com.example.test.dao.Dao
import com.example.test.api.ApiService
import com.example.test.api.RetrofitClient
import com.example.test.model.Customer
import com.example.test.model.IListable
import com.example.test.model.Product
import com.example.test.utils.toDomain
import com.example.test.utils.toEntity

class AppRepositoryImpl(
    private val appDao: Dao,
    apiService: ApiService? = null
) : AppRepository {

    private val apiService: ApiService =
        apiService ?: RetrofitClient.apiService

    // ============= ОСНОВНІ МЕТОДИ (БЕЗ ЗМІН) =============

    override suspend fun getAllData(): List<IListable> {
        val products = getAllProducts()
        val customers = getAllCustomers()

        val result = mutableListOf<IListable>()
        result.addAll(products)
        result.addAll(customers)

        return result
    }

    override suspend fun getAllCustomers(): List<Customer> {
        return appDao.getAllCustomers().map { it.toDomain() }
    }

    override suspend fun getCustomerById(id: Int): Customer {
        return appDao.getCustomerById(id).toDomain()
    }

    override suspend fun insertAllCustomers(customers: List<Customer>) {
        appDao.insertAllCustomers(customers.map { it.toEntity() })
    }

    override suspend fun insertCustomer(customer: Customer) {
        appDao.insertCustomer(customer.toEntity())
    }

    override suspend fun deleteCustomer(customer: Customer) {
        appDao.deleteCustomer(customer.toEntity())
    }

    override suspend fun deleteAllCustomers() {
        appDao.deleteAllCustomers()
    }

    override suspend fun updateCustomer(customer: Customer) {
        appDao.updateCustomer(customer.toEntity())
    }

    override suspend fun getAllProducts(): List<Product> {
        val products = appDao.getAllProducts()
        val customers = appDao.getAllCustomers()
        val customerMap = customers.associateBy { it.id }

        return products.mapNotNull { product ->
            customerMap[product.customerId]?.let { customer ->
                product.toDomain(customer.toDomain())
            }
        }
    }

    override suspend fun getProductById(productId: Long): Product {
        val product = appDao.getProductById(productId)
        val customer = appDao.getCustomerById(product.customerId.toInt())
        return product.toDomain(customer.toDomain())
    }

    override suspend fun getProductsByCustomerId(customerId: Long): List<Product> {
        val products = appDao.getProductsByCustomerId(customerId)
        val customer = appDao.getCustomerById(customerId.toInt()).toDomain()
        return products.map { it.toDomain(customer) }
    }

    override suspend fun insertAllProducts(products: List<Product>) {
        appDao.insertAllProducts(*products.map { it.toEntity() }.toTypedArray())
    }

    override suspend fun insertProduct(product: Product) {
        appDao.insertProduct(product.toEntity())
    }

    override suspend fun deleteProduct(product: Product) {
        appDao.deleteProduct(product.toEntity())
    }

    override suspend fun deleteAllProducts() {
        appDao.deleteAllProducts()
    }

    override suspend fun updateProduct(product: Product) {
        appDao.updateProduct(product.toEntity())
    }

    override suspend fun populateTestData() {
        val testCustomers = listOf(
            Customer(name = "Іван Петренко", phone = "+380992223344"),
            Customer(name = "Марія Коваленко", phone = "+380991111111"),
            Customer(name = "Олег Сидоренко", phone = "+380992222222")
        )

        deleteAllProducts()
        deleteAllCustomers()

        insertAllCustomers(testCustomers)

        val customersFromDb = getAllCustomers()

        val testProducts = listOf(
            Product(title = "Ноутбук", price = 25000.0, customer = customersFromDb[0]),
            Product(title = "Телефон", price = 15000.0, customer = customersFromDb[1]),
            Product(title = "Планшет", price = 8000.0, customer = customersFromDb[2])
        )

        insertAllProducts(testProducts)
    } // удалить

    override suspend fun fetchAndSaveDataFromApi(): Result<Unit> {
        return try {

            Log.d("AppRepository", "Початок завантаження даних з API...")

            val customersResponse = apiService.getCustomers()
            Log.d("AppRepository", "Отримано ${customersResponse.size} customers з API")

            val customerEntities = customersResponse.map { it.toEntity() }
            appDao.insertAllCustomers(customerEntities)
            Log.d("AppRepository", "Збережено ${customerEntities.size} customers в БД")

            val productsResponse = apiService.getProducts()
            Log.d("AppRepository", "Отримано ${productsResponse.size} products з API")

            val productEntities = productsResponse.map { it.toEntity() }
            appDao.insertAllProducts(*productEntities.toTypedArray())
            Log.d("AppRepository", "Збережено ${productEntities.size} products в БД")

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AppRepository", "Помилка при завантаженні даних з API", e)
            Result.failure(e)
        }
    }

    override suspend fun syncWithApi(): Result<String> {
        return try {

            deleteAllProducts()
            deleteAllCustomers()

            val result = fetchAndSaveDataFromApi()

            if (result.isFailure) {
                return Result.failure(result.exceptionOrNull() ?: Exception("Невідома помилка"))
            }

            val customersCount = getAllCustomers().size
            val productsCount = getAllProducts().size

            Result.success("Синхронізовано: $customersCount клієнтів, $productsCount товарів")
        } catch (e: Exception) {
            Log.e("AppRepository", "Помилка синхронізації", e)
            Result.failure(e)
        }
    }
}