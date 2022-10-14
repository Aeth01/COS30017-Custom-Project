package com.example.customproject.database

import androidx.room.*

@Dao
interface ConcreteItemDao {
    @Query("SELECT * FROM concreteitem")
    fun getAll() : List<ConcreteItem>

    @Query("SELECT * FROM concreteitem WHERE itemId=(:id)")
    fun getById(id : Int): List<ConcreteItem>

    @Query("UPDATE concreteitem SET item_name=(:name), brand_id=(:brandId), price=(:price), date=(:date), seller=(:seller) WHERE itemId=(:id)")
    fun updateById(id : Int, name : String, brandId : Int, price : Float, date : String, seller : String)

    @Insert
    fun insertAll(vararg users : ConcreteItem)

    @Delete
    fun delete(item : ConcreteItem)
}