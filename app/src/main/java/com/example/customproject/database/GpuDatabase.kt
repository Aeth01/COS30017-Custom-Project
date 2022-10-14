package com.example.customproject.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ConcreteItem::class, BrandItem::class], version = 1)
abstract class GpuDatabase : RoomDatabase() {
    abstract fun itemDao(): ConcreteItemDao
    abstract fun brandDao(): BrandDao
}