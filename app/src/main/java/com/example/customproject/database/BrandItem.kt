package com.example.customproject.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName="brand_table")
data class BrandItem(
    @PrimaryKey val brandName : String = "",
) : Parcelable
{
    companion object {
        fun brandItemListToStringList(list : List<BrandItem>) : List<String> {
            val ret = mutableListOf<String>()

            for(item in list) {
                ret.add(item.brandName)
            }

            return ret
        }
    }
}