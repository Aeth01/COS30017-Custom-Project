package com.example.customproject

import androidx.lifecycle.ViewModel
import com.example.customproject.database.ConcreteItem
import com.example.customproject.database.ConcreteItemDao
import kotlinx.coroutines.flow.Flow

class StatisticsViewModel(private val concreteItemDao : ConcreteItemDao) : ViewModel() {
    fun getData() : Flow<List<ConcreteItem>> {
        return concreteItemDao.getAll()
    }
}