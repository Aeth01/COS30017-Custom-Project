package com.example.customproject.ui.viewitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.database.BrandDao
import com.example.customproject.database.ConcreteItemDao
import java.lang.IllegalArgumentException

class ViewItemsViewModelFactory(private val itemDao: ConcreteItemDao, private val brandDao : BrandDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewItemsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewItemsViewModel(itemDao, brandDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}