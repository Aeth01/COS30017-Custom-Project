package com.example.customproject.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BrandDao {
    @Query("SELECT * FROM brand_table ORDER BY brandName ASC")
    fun getAll() : Flow<List<BrandItem>>

    @Query("SELECT * FROM brand_table WHERE brandName=(:name)")
    fun getBrandByName(name : Int) : List<BrandItem>

    @Query("UPDATE brand_table SET brandName=(:name) WHERE brandName=(:name)")
    suspend fun updateById(name : String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg brandItem : BrandItem)

    @Delete
    suspend fun delete(brand : BrandItem)
}