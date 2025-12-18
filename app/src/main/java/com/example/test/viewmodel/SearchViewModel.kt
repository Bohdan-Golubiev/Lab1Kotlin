package com.example.test.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.MyDbApp
import com.example.test.model.Customer
import com.example.test.model.IListable
import com.example.test.model.Product
import com.example.test.repository.AppRepositoryImpl
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepositoryImpl = (application as MyDbApp).appRepository

    private val _items = mutableStateOf<List<IListable>>(emptyList())
    val items: State<List<IListable>> = _items

    private val _message = mutableStateOf("Натисніть кнопку для завантаження даних")
    val message: State<String> = _message

    fun loadFromDatabase() {
        viewModelScope.launch {
            try {
                _message.value = "Завантаження з БД..."

                val allData = repository.getAllData()

                _items.value = allData
                _message.value = if (allData.isEmpty()) {
                    "БД порожня.\nЗавантажте дані з API."
                } else {
                    "Завантажено ${allData.size} записів з БД"
                }

            } catch (e: Exception) {
                _message.value = "Помилка завантаження з БД: ${e.message}"
            }
        }
    }

    fun loadFromApi() {
        viewModelScope.launch {
            try {
                _message.value = "Завантаження з API..."

                val result = repository.fetchAndSaveDataFromApi()

                result.onSuccess {
                    loadFromDatabase()
                }.onFailure { error ->
                    _message.value = "Помилка API: ${error.message}"
                }

            } catch (e: Exception) {
                _message.value = "Помилка: ${e.message}"
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            try {
                repository.deleteAllProducts()
                repository.deleteAllCustomers()

                _items.value = emptyList()
                _message.value = "БД успішно очищено"

            } catch (e: Exception) {
                _message.value = "Помилка очищення: ${e.message}"
            }
        }
    }

    fun increasePriceBy100(product: Product) {
        viewModelScope.launch {
            try {
                repository.updateProduct(product.copy(price = product.price + 100))
                loadFromDatabase()
            } catch (e: Exception) {
                _message.value = "Помилка оновлення: ${e.message}"
            }
        }
    }

    fun decreasePriceBy100(product: Product) {
        viewModelScope.launch {
            try {
                repository.updateProduct(product.copy(price = maxOf(0.0, product.price - 100)))
                loadFromDatabase()
            } catch (e: Exception) {
                _message.value = "Помилка оновлення: ${e.message}"
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(product)
                loadFromDatabase()
            } catch (e: Exception) {
                _message.value = "Помилка видалення: ${e.message}"
            }
        }
    }

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            try {
                repository.deleteCustomer(customer)
                loadFromDatabase()
            } catch (e: Exception) {
                _message.value = "Помилка видалення: ${e.message}"
            }
        }
    }

    init {
        loadFromDatabase()
    }
}