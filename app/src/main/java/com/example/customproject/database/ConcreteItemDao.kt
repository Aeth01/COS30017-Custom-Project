package com.example.customproject.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ConcreteItemDao {
    @Query("SELECT * FROM item_table ORDER BY date DESC")
    fun getAll() : Flow<List<ConcreteItem>>

    @Query("SELECT * FROM item_table WHERE itemId=(:id)")
    fun getById(id : Int): List<ConcreteItem>

    @Query("UPDATE item_table SET item_name=(:name), brand_id=(:brandId), price=(:price), date=(:date), seller=(:seller) WHERE itemId=(:id)")
    suspend fun updateById(id : Int, name : String, brandId : Int, price : Float, date : String, seller : String)

    @Insert
    suspend fun insertAll(vararg users : ConcreteItem)

    @Delete
    suspend fun delete(item : ConcreteItem)
}