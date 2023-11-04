package com.cucu.cucuadminpanel.application

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.cucu.cucuadminpanel.data.models.Product
import java.util.Locale
import kotlin.math.roundToInt


fun String.firstCharToUpperCase() : String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault())
        else it.toString()
    }
}

fun textWithLineThrough(text:String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
            append(text)
        }
    }
}

fun calculateDiscountPercent(product: Product?): String {
    val result = 0
    product?.let {
        val diff = product.oldPrice?.roundToInt()!! - product.newPrice?.roundToInt()!!
        val percent = (diff / product.oldPrice) * 100
        return percent.toInt().toString()
    }
    return result.toString()
}

//Create bitmap to reduce the weight later
fun getBitmapFromUri(context: Context, uri: Uri) : Bitmap? {
    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
    return bitmap
}