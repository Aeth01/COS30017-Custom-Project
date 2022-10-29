package com.example.customproject.ui.deleteConfirmDialog

import androidx.lifecycle.ViewModel
import com.example.customproject.database.BrandDao
import com.example.customproject.database.BrandItem

class DeleteBrandViewModel(private val brandDao : BrandDao) : ViewModel() {
    suspend fun deleteBrand(brand : BrandItem) {
        return brandDao.delete(brand)
    }
}