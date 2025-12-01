package com.example.test

class SettingsRepository {
    fun getSettings(): List<SettingItem> {
        return listOf(
            SettingCategory(
                id = "cat1",
                categoryName = "Загальні налаштування",
            ),
            SettingToggle(
                id = "toggle1",
                title = "Темна тема",
                description = "Увімкнути темне оформлення додатку",
                isEnabled = true
            ),
            SettingToggle(
                id = "toggle2",
                title = "Сповіщення",
                description = "Отримувати push-сповіщення",
                isEnabled = false
            ),
            SettingOption(
                id = "option1",
                title = "Мова інтерфейсу",
                options = listOf("Українська", "English", "Русский"),
                selectedOption = "Українська"
            ),
            SettingCategory(
                id = "cat2",
                categoryName = "Конфіденційність",
            ),
            SettingToggle(
                id = "toggle3",
                title = "Аналітика",
                description = "Надсилати дані про використання",
                isEnabled = true
            ),
            SettingOption(
                id = "option2",
                title = "Збереження історії",
                options = listOf("1 день", "3 дні", "7 днів", "30 днів", "Завжди", "Ніколи"),
                selectedOption = "30 днів"
            ),
            SettingCategory(
                id = "cat3",
                categoryName = "Про додаток",
            ),
            SettingOption(
                id = "option3",
                title = "Скріни додатку",
                options = listOf(AppDestinations.SEARCH.label, AppDestinations.MAIN.label, AppDestinations.SETTINGS.label),
                selectedOption = AppDestinations.SETTINGS.label
            )
        )
    }
}
sealed class SettingItem {
    abstract val id: String
}

data class SettingToggle(
    override val id: String,
    val title: String,
    val description: String,
    val isEnabled: Boolean
) : SettingItem()

data class SettingOption(
    override val id: String,
    val title: String,
    val options: List<String>,
    val selectedOption: String
) : SettingItem()

data class SettingCategory(
    override val id: String,
    val categoryName: String,
) : SettingItem()