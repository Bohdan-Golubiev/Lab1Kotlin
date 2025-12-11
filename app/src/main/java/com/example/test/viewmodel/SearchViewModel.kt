package com.example.test.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.repository.AppRepositoryImpl
import com.example.test.MyDbApp
import com.example.test.model.Customer
import com.example.test.model.IListable
import com.example.test.model.Product
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepositoryImpl = (application as MyDbApp).appRepository

    private val _items = mutableStateOf<List<IListable>>(emptyList())
    val items: State<List<IListable>> = _items

    private val _message = mutableStateOf("Натисніть Search для завантаження даних")
    val message: State<String> = _message

    fun search() {
        viewModelScope.launch {
            try {
                val allData = repository.getAllData()

                _items.value = allData
                _message.value = "Завантажено ${allData.size} записів"

            } catch (e: Exception) {
                _message.value = "Помилка: ${e.message}"
            }
        }
    }

    fun increasePriceBy100(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product.copy(price = product.price + 100))
            search()
        }
    }

    fun decreasePriceBy100(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product.copy(price = maxOf(0.0, product.price - 100)))
            search()
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
            search()
        }
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            repository.deleteCustomer(customer)
            search()
        }
    }
    fun resetDatabase() {
        viewModelScope.launch {
            repository.populateTestData()
            search()
        }
    }
    fun clearDatabase()
    {
        viewModelScope.launch {
            repository.deleteAllProducts()
            repository.deleteAllCustomers()
            search()
        }
    }
}
