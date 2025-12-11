package com.example.test.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "customers"
)
data class CustomerEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    @ColumnInfo(name = "customer_id")
    val id: Long = 0,

    @ColumnInfo(name = "customer_name")
    var name: String,

    @ColumnInfo(name = "phone_number")
    val phone: String
)