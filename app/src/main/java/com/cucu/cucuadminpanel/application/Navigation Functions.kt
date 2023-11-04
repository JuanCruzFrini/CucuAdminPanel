package com.cucu.cucuadminpanel.application

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

inline fun <reified T : Parcelable> NavGraphBuilder.arguedComposable(
    route:String,
    argument:String,
    navController: NavHostController,
    crossinline content: @Composable (T?) -> Unit
) {
    this.composable(
        route = route,
        arguments = listOf(navArgument(argument){ type = NavType.parcelableTypeOf<T>() })
    ) {
        val value = navController.previousBackStackEntry?.savedStateHandle?.get<T>(argument)
        content(value)
    }
}