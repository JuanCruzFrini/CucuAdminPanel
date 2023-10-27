package com.cucu.cucuadminpanel.data.models

import android.os.Parcelable
import com.cucu.cucuadminpanel.data.models.items.ItemCategory
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Product(
    @get:Exclude
    var id:String?="",
    var name:String?="",
    var newPrice:Double?=0.0,
    val oldPrice:Double?=0.0,
    var stock:Int?=0,
    var img:String?="",
    var description:String?="",
    val code:Long?=0L,
    val seenTimes:Int?=0,
    val soldTimes:Int?=0,
    val favTimes:Int?=0,
    @Contextual
    val category: ItemCategory? = ItemCategory()
) : Parcelable {

    fun isDiscount(): Boolean{
        return if (newPrice!=null && oldPrice!=null){
            newPrice!!.toInt() < oldPrice.toInt()
        } else false
    }
}