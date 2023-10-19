package com.cucu.cucuadminpanel.data.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Notification(
    @get:Exclude
    var id:String?="",
    val title:String?="",
    val content:String?="",
    val purchaseId:String?="",
    @ServerTimestamp
    val date: Date? = null,
    val hasBeenOpen:Boolean? = false
)
