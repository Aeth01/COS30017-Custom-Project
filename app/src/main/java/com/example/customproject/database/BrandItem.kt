package com.example.customproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="brand_table")
data class BrandItem(
    @PrimaryKey val brandName : String = "",
)