package com.example.customproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customproject.database.ConcreteItem
import com.example.customproject.database.ConcreteItemDao
import kotlinx.coroutines.launch

class AddItemViewModel(private val concreteItemDao: ConcreteItemDao) : ViewModel() {
    // add item to table from item details
    fun addNewItem(itemName : String, brandId : Int, itemPrice : Float, priceDate : String, itemSeller : String) {
        val newConcreteItem = getNewItem(itemName, brandId, itemPrice, priceDate, itemSeller)
        insertItem(newConcreteItem)
    }

    // insert a new row into the table
    private fun insertItem(item : ConcreteItem) {
        // insert row as coroutine
        viewModelScope.launch {
            concreteItemDao.insertAll(item)
        }
    }

    // return new instance of row
    private fun getNewItem(itemName : String, brandId : Int, itemPrice : Float, priceDate : String, itemSeller : String) : ConcreteItem {
        return ConcreteItem(
            name=itemName,
            brand=brandId,
            price=itemPrice,
            date=priceDate,
            seller=itemSeller)
    }
}