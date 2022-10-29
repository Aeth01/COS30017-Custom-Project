package com.example.customproject.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.lang.NumberFormatException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity(
    tableName="item_table",
    foreignKeys = [ForeignKey(
        entity = BrandItem::class,
        parentColumns = arrayOf("brandName"),
        childColumns = arrayOf("brand_name"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class ConcreteItem(
    @PrimaryKey(autoGenerate = true) val itemId : Int = 0,
    @ColumnInfo(name="item_name") val name : String,
    @ColumnInfo(name="brand_name", index = true) val brand : String = "",
    @ColumnInfo(name="price") val price : Float,
    @ColumnInfo(name="date") val date : String,
    @ColumnInfo(name="seller") val seller : String
) : Parcelable {
    companion object {
        // check date valid
        @Throws(ParseException::class)
        fun validDate(date : String) : Boolean {
            // if parse successful, return true. Else throw parse exception
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            sdf.isLenient = false
            sdf.parse(date)

            return true
        }

        // check price is valid
        @Throws(NumberFormatException::class)
        fun validPrice(price : String) : Boolean {
            if (price.toFloat() <= 0F) {
                throw NumberFormatException()
            }

            return true
        }
    }
}