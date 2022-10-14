package com.example.customproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BrandItem(
    @PrimaryKey val brandId : Int,
    @ColumnInfo(name="brand_name") val name : String
)