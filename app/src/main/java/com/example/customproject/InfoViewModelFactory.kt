package com.example.customproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.database.BrandDao
import java.lang.IllegalArgumentException

class InfoViewModelFactory(private val brandDao: BrandDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InfoViewModel(brandDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}