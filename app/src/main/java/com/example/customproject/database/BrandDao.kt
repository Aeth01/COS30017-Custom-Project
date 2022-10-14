package com.example.customproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BrandDao {
    @Query("SELECT * FROM branditem")
    fun getAll() : List<BrandItem>

    @Query("SELECT * FROM branditem WHERE brandId=(:id)")
    fun getBrandById(id : Int) : List<BrandItem>

    @Query("UPDATE branditem SET brand_name=(:name) WHERE brandId=(:id)")
    fun updateById(id : Int, name : String)

    @Insert
    fun insertAll(vararg brandItem : BrandItem)

    @Delete
    fun delete(brand : BrandItem)
}