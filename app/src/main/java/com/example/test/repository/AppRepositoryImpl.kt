package com.example.test.repository

import com.example.test.dao.Dao
import com.example.test.model.Customer
import com.example.test.model.CustomerList
import com.example.test.model.IListable
import com.example.test.model.Product
import com.example.test.model.ProductList
import com.example.test.utils.toDomain
import com.example.test.utils.toEntity

class AppRepositoryImpl(private val appDao: Dao): AppRepository {

    val customersList = listOf(
        Customer(
            name = "Іван Петренко",
            phone = "+380992223344"
        ),
        Customer(
            name = "Марія Коваленко",
            phone = "+380991111111"
        ),
        Customer(
            name = "Олег Сидоренко",
            phone = "+380992222222"
        ),
        Customer(
            name = "Анна Шевченко",
            phone = "+380993333333"
        ),
        Customer(
            name = "Дмитро Бондаренко",
            phone = "+380994444444"
        ),
        Customer(
            name = "Юлія Мельник",
            phone = "+380995555555"
        )
    )

    fun getProducts(customers: List<Customer>): List<Product> {
        return listOf(
            Product(
                title = "Ноутбук",
                price = 25000.0,
                customer = customers[0]
            ),
            Product(
                title = "Телефон",
                price = 15000.0,
                customer = customers[1]
            ),
            Product(
                title = "Планшет",
                price = 8000.0,
                customer = customers[2]
            ),
            Product(
                title = "Навушники",
                price = 2000.0,
                customer = customers[3]
            ),
            Product(
                title = "Клавіатура",
                price = 1500.0,
                customer = customers[4]
            ),
            Product(
                title = "Миша",
                price = 800.0,
                customer = customers[5]
            ),
            Product(
                title = "Монітор",
                price = 12000.0,
                customer = customers[0]
            ),
            Product(
                title = "Веб-камера",
                price = 3000.0,
                customer = customers[2]
            )
        )
    }

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
        deleteAllProducts()
        deleteAllCustomers()

        insertAllCustomers(customersList)

        val customersFromDb = getAllCustomers()

        val productsToInsert = getProducts(customersFromDb)
        insertAllProducts(productsToInsert)
    }

}