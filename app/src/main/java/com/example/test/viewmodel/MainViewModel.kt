package com.example.test.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _message = mutableStateOf("Welcome on main page")
    val message: State<String> = _message

    fun onButtonClick() {
        _message.value = "Button pressed on main page"
    }
}