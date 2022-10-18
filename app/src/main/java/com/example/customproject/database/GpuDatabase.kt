package com.example.customproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConcreteItem::class, BrandItem::class], version = 2, exportSchema = false )
abstract class GpuDatabase : RoomDatabase() {
    abstract fun itemDao(): ConcreteItemDao
    abstract fun brandDao(): BrandDao

    companion object {
        @Volatile
        private var INSTANCE : GpuDatabase? = null
        fun getDatabase(context : Context): GpuDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GpuDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}