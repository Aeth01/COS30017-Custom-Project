package com.example.customproject

import androidx.lifecycle.ViewModel
import com.example.customproject.database.BrandDao
import com.example.customproject.database.BrandItem
import com.example.customproject.database.ConcreteItem
import kotlinx.coroutines.flow.Flow

class InfoViewModel(private val brandDao: BrandDao) : ViewModel() {
    var id : Int = 0
    var name : String? = null
    var brand : String? = null
    var price : Float = 0F
    var seller : String? = null
    var date : String? = null

    fun getBrands() : Flow<List<BrandItem>> {
        return brandDao.getAll()
    }

    fun setVariables(item : ConcreteItem) {
        id = item.itemId
        name = item.name
        brand = item.brand
        price = item.price
        seller = item.seller
        date = item.date
    }
}