package com.cucu.cucuadminpanel.data.models.items

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

//para favoritos e historial
data class ItemSaved(
    @get:Exclude
    var documentId:String?="",
    val productId:String?="",
    @ServerTimestamp
    val date: Date?=null
)