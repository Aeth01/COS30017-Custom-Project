package com.example.customproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customproject.database.ConcreteItem
import com.example.customproject.database.ConcreteItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewItemsViewModel(private val concreteItemDao: ConcreteItemDao) : ViewModel() {
    var clickedIndex = -1
    fun getRows() : Flow<List<ConcreteItem>> {
        return concreteItemDao.getAll()
    }

    fun getByBrand(brandId : String) : Flow<List<ConcreteItem>> {
        return concreteItemDao.getByBrand(brandId)
    }

    fun updateById(id : Int, name : String, brandId : String, price : Float, date : String, seller : String) {
        viewModelScope.launch {
            concreteItemDao.updateById(id, name, brandId, price, date, seller)
        }
    }
}
