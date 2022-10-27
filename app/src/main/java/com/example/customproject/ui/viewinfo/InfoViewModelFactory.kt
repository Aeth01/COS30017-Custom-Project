package com.example.customproject.ui.viewinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.database.BrandDao
import com.example.customproject.database.ConcreteItemDao
import java.lang.IllegalArgumentException

class InfoViewModelFactory(private val brandDao: BrandDao, private val itemDao: ConcreteItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InfoViewModel(brandDao, itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}