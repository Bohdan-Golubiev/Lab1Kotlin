package com.example.test

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class SettingsViewModel : ViewModel() {

    var status by mutableStateOf("Settings not saved")
        private set

    fun updateStatus() {
        status = "Settings saved"
    }
}