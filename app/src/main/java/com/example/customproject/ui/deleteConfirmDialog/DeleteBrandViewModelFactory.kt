package com.example.customproject.ui.deleteConfirmDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.database.BrandDao
import com.example.customproject.ui.statistics.StatisticsViewModel
import java.lang.IllegalArgumentException

class DeleteBrandViewModelFactory(private val brandDao : BrandDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeleteBrandViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeleteBrandViewModel(brandDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}