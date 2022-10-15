package com.example.customproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.customproject.database.ConcreteItemDao

class AddItemViewModelFactory(private val concreteItemDao: ConcreteItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddItemViewModel(concreteItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}