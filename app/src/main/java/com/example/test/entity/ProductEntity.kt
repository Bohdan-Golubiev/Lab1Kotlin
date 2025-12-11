package com.example.test.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = CustomerEntity::class,
            parentColumns = arrayOf("customer_id"),
            childColumns = arrayOf("customer_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    @ColumnInfo(name = "product_id")
    val id: Long = 0,

    @ColumnInfo(name = "product_title")
    val title: String,

    val price: Double,

    @ColumnInfo(name = "customer_id", index = true)
    val customerId: Long
)