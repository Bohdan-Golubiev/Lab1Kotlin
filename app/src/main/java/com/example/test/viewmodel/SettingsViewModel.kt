package com.example.test.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.test.SettingItem
import com.example.test.SettingOption
import com.example.test.SettingToggle
import com.example.test.SettingsRepository

class SettingsViewModel : ViewModel() {
    private val repository = SettingsRepository()

    private val _settings = mutableStateOf<List<SettingItem>>(emptyList())
    val settings: State<List<SettingItem>> = _settings

    private val _status = mutableStateOf("Готово до збереження")
    val status: State<String> = _status

    init {
        loadSettings()
    }

    private fun loadSettings() {
        _settings.value = repository.getSettings()
    }

    fun toggleSetting(id: String) {
        _settings.value = _settings.value.map { item ->
            if (item is SettingToggle && item.id == id) {
                item.copy(isEnabled = !item.isEnabled)
            } else {
                item
            }
        }
        _status.value = "Є незбережені зміни"
    }

    fun selectOption(id: String, option: String) {
        _settings.value = _settings.value.map { item ->
            if (item is SettingOption && item.id == id) {
                item.copy(selectedOption = option)
            } else {
                item
            }
        }
        _status.value = "Є незбережені зміни"
    }

    fun updateStatus() {
        _status.value = "Зміни збережено успішно!"
    }
}