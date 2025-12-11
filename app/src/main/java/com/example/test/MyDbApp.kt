package com.example.test

import android.app.Application
import com.example.test.bd.AppDb
import com.example.test.repository.AppRepositoryImpl

class MyDbApp : Application() {
    private val appDb by lazy { AppDb.getDatabase(this) }
    val appRepository by lazy { AppRepositoryImpl(appDb.dao()) }
}