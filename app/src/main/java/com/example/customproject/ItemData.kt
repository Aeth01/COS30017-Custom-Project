package com.example.customproject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ItemData(
    var name : String = "",
    var brand : String = "",
    var price : Float = 0F,
    var date : String = "",
    var seller : String = "" ) : Parcelable {
}