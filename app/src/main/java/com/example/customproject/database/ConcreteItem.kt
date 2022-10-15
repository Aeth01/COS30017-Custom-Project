package com.example.customproject.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName="item_table",
    foreignKeys = [ForeignKey(
        entity = BrandItem::class,
        parentColumns = arrayOf("brandId"),
        childColumns = arrayOf("brand_id"),
        onDelete = ForeignKey.SET_NULL
    )]
)
data class ConcreteItem(
    @PrimaryKey(autoGenerate = true) val itemId : Int = 0,
    @ColumnInfo(name="item_name") val name : String,
    @ColumnInfo(name="brand_id", index = true) val brand : Int? = null,
    @ColumnInfo(name="price") val price : Float,
    @ColumnInfo(name="date") val date : String,
    @ColumnInfo(name="seller") val seller : String
) : Parcelable