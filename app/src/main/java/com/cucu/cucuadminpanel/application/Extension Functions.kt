package com.cucu.cucuadminpanel.application

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavType
import com.cucu.cucuadminpanel.data.models.Product
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt


//Necesario para compartir objetos entre screens con compose navigation
inline fun <reified T : Parcelable> NavType.Companion.parcelableTypeOf() = object : NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            bundle.getParcelable(key, T::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }
    override fun parseValue(value: String): T = Json.decodeFromString(Uri.decode(value))
    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)
}

//Necesario para deserializar los valores de tipo Date en las data classes
object NullableDateSerializer : KSerializer<Date?> {
    private val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NullableDate", PrimitiveKind.STRING)

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Date?) {
        if (value != null) {
            encoder.encodeString(dateFormat.format(value))
        } else {
            encoder.encodeNull()
        }
    }

    override fun deserialize(decoder: Decoder): Date? {
        val dateString = decoder.decodeString()
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
}

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