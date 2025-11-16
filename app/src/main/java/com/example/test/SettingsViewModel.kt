package com.example.test

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _status = mutableStateOf("Settings not saved")
    val status: State<String> = _status

    fun updateStatus() {
        _status.value = "Settings saved"
    }
}
