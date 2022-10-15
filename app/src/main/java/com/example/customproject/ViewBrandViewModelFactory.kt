package com.example.customproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.database.BrandDao
import java.lang.IllegalArgumentException

class ViewBrandViewModelFactory(private val brandDao: BrandDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewBrandViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewBrandViewModel(brandDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}