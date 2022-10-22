package com.example.customproject

import androidx.lifecycle.ViewModel
import com.example.customproject.database.BrandDao
import com.example.customproject.database.BrandItem
import com.example.customproject.database.ConcreteItem
import com.example.customproject.database.ConcreteItemDao
import kotlinx.coroutines.flow.Flow

class InfoViewModel(private val brandDao: BrandDao, private val itemDao: ConcreteItemDao) : ViewModel() {
    var id : Int = 0
    var name : String? = null
    var brand : String? = null
    var price : Float = 0F
    var seller : String? = null
    var date : String? = null

    // item reference for item being viewed
    var itemRef : ConcreteItem? = null

    // get list of brands via DAO
    fun getBrands() : Flow<List<BrandItem>> {
        return brandDao.getAll()
    }

    // delete item via item DAO
    suspend fun deleteItem(item : ConcreteItem) {
        return itemDao.delete(item)
    }

    // set variables of current item
    fun setVariables(item : ConcreteItem) {
        id = item.itemId
        name = item.name
        brand = item.brand
        price = item.price
        seller = item.seller
        date = item.date

        itemRef = item
    }
}