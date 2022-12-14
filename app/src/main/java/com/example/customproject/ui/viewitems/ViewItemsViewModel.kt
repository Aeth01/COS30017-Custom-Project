package com.example.customproject.ui.viewitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customproject.database.BrandDao
import com.example.customproject.database.ConcreteItem
import com.example.customproject.database.ConcreteItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewItemsViewModel(private val itemDao: ConcreteItemDao) : ViewModel() {
    var clickedIndex = -1

    fun getByBrand(brandId : String) : Flow<List<ConcreteItem>> {
        return itemDao.getByBrand(brandId)
    }

    fun updateById(id : Int, name : String, brandId : String, price : Float, date : String, seller : String) {
        viewModelScope.launch {
            itemDao.updateById(id, name, brandId, price, date, seller)
        }
    }
}
