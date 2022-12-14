package com.example.customproject.ui.viewbrand

import androidx.lifecycle.ViewModel
import com.example.customproject.database.BrandDao
import com.example.customproject.database.BrandItem
import kotlinx.coroutines.flow.Flow

class ViewBrandViewModel(private val brandDao: BrandDao) : ViewModel() {
    // get rows via DAO
    fun getRows() : Flow<List<BrandItem>> {
        return brandDao.getAll()
    }

    suspend fun deleteBrand(item : BrandItem) {
        return brandDao.delete(item)
    }
}