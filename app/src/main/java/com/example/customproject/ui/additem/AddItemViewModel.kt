package com.example.customproject.ui.additem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customproject.database.BrandDao
import com.example.customproject.database.BrandItem
import com.example.customproject.database.ConcreteItem
import com.example.customproject.database.ConcreteItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AddItemViewModel(private val concreteItemDao : ConcreteItemDao,
                       private val brandDao : BrandDao) : ViewModel() {
    // add item to table from item details
    fun addNewItem(itemName : String, brandName : String, itemPrice : Float, priceDate : String, itemSeller : String) {
        val newConcreteItem = getNewItem(itemName, brandName, itemPrice, priceDate, itemSeller)
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
    private fun getNewItem(itemName : String, brandName : String, itemPrice : Float, priceDate : String, itemSeller : String) : ConcreteItem {
        return ConcreteItem(
            name=itemName,
            brand=brandName,
            price=itemPrice,
            date=priceDate,
            seller=itemSeller)
    }

    fun getBrands() : Flow<List<BrandItem>> {
        return brandDao.getAll()
    }
}