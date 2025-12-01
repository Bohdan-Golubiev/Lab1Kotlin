package com.example.test.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _message = mutableStateOf("Search page")
    val message: State<String> = _message

    fun search() {
        _message.value = "Searching..."
    }
}