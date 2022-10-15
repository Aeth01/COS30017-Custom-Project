package com.example.customproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.database.ConcreteItemDao
import java.lang.IllegalArgumentException

class ViewItemsViewModelFactory(private val concreteItemDao: ConcreteItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewItemsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewItemsViewModel(concreteItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}