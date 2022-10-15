package com.example.customproject

import androidx.lifecycle.ViewModel
import com.example.customproject.database.BrandDao
import com.example.customproject.database.BrandItem
import com.example.customproject.database.ConcreteItem
import kotlinx.coroutines.flow.Flow

class ViewBrandViewModel(private val brandDao: BrandDao) : ViewModel() {
    fun getRows() : Flow<List<BrandItem>> {
        return brandDao.getAll()
    }
}