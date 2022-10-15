package com.example.customproject

import android.app.Application
import com.example.customproject.database.GpuDatabase

class DatabaseApplication : Application() {
    val database : GpuDatabase by lazy {
        GpuDatabase.getDatabase(this)
    }
}