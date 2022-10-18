package com.example.customproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BrandDao {
    @Query("SELECT * FROM brand_table ORDER BY brandId ASC")
    fun getAll() : Flow<List<BrandItem>>

    @Query("SELECT * FROM brand_table WHERE brandId=(:id)")
    fun getBrandById(id : Int) : List<BrandItem>

    @Query("SELECT * FROM brand_table WHERE brand_name=(:name)")
    fun getBrandByName(name : String) : List<BrandItem>

    @Query("UPDATE brand_table SET brand_name=(:name) WHERE brandId=(:id)")
    suspend fun updateById(id : Int, name : String)

    @Insert
    suspend fun insertAll(vararg brandItem : BrandItem)

    @Delete
    suspend fun delete(brand : BrandItem)
}