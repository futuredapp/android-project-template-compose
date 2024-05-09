package app.futured.androidprojecttemplate.tools.extensions

import android.util.Base64.NO_PADDING
import android.util.Base64.NO_WRAP
import android.util.Base64.URL_SAFE
import android.util.Base64.decode
import android.util.Base64.encodeToString
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> SavedStateHandle.getSerializedArgument(key: String): T? = get<String>(key)?.deserializeFromNavArgument()

inline fun <reified T> SavedStateHandle.getRequiredSerializedArgument(key: String): T =
    getSerializedArgument(key) ?: error("Required parameter is not present")

inline fun <reified T> SavedStateHandle.getArgument(key: String): T? = safe { get<T>(key) }

inline fun <reified T> SavedStateHandle.getRequiredArgument(key: String): T =
    getArgument(key) ?: getSerializedArgument(key) ?: error("Required parameter is not present")

inline fun <reified T : Any> T.serializeAsNavArgument(): String? =
    encodeToString(Json.encodeToString(this).encodeToByteArray(), NO_PADDING or NO_WRAP or URL_SAFE)

inline fun <reified T> String.deserializeFromNavArgument(): T? =
    safe { Json.decodeFromString(decode(this, NO_PADDING or NO_WRAP or URL_SAFE).decodeToString()) }

fun <T> SavedStateHandle.subscribeForResult(key: String, callback: (T) -> Unit) {
    val liveData = this.getLiveData<T>(key)
    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            if (t != null) {
                val value = remove<T>(key)
                if (value != null) {
                    callback(t)
                }
                liveData.removeObserver(this)
                subscribeForResult(key, callback)
            }
        }
    }
    liveData.observeForever(observer)
}
