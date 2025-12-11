package com.example.test.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.test.dao.Dao
import com.example.test.entity.CustomerEntity
import com.example.test.entity.ProductEntity

@Database(entities = [CustomerEntity::class, ProductEntity::class], version = 1, exportSchema = true)
abstract class AppDb : RoomDatabase() {

    abstract fun dao(): Dao

    companion object {
        // Volatile annotation ensures that the INSTANCE variable is always up-to-date
        // and visible to all threads.
        @Volatile
        private var INSTANCE: AppDb? = null

        // This method provides a singleton instance of the database.
        fun getDatabase(context: Context): AppDb {
            // If INSTANCE is not null, return it; otherwise, create a new database instance.
            return INSTANCE ?: synchronized(this) { // Ensures only one thread creates the database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "app_database.db3" // The name of your database file
                )
                    // .addMigrations(MIGRATION_1_2) // For production, implement proper migrations
                    .build()
                INSTANCE = instance
                // Return instance
                instance
            }
        }
    }
}