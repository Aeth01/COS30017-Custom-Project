package com.example.customproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customproject.database.ConcreteItem
import com.example.customproject.database.ConcreteItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewItemsViewModel(private val concreteItemDao: ConcreteItemDao) : ViewModel() {
    fun getRows() : Flow<List<ConcreteItem>> {
        return concreteItemDao.getAll()
    }
}
