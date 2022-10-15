package com.example.customproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="brand_table")
data class BrandItem(
    @PrimaryKey(autoGenerate = true) val brandId : Int = 0,
    @ColumnInfo(name="brand_name") val name : String
)