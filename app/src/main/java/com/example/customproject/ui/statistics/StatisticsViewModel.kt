package com.example.customproject.ui.statistics

import androidx.lifecycle.ViewModel
import com.example.customproject.database.ConcreteItem
import com.example.customproject.database.ConcreteItemDao
import kotlinx.coroutines.flow.Flow

class StatisticsViewModel(private val concreteItemDao : ConcreteItemDao) : ViewModel() {
    // get concrete item data via DAO
    fun getData() : Flow<List<ConcreteItem>> {
        return concreteItemDao.getAll()
    }
}