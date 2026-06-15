package app.futured.androidprojecttemplate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember

class ResultStore {
    // A SnapshotStateMap ensures Compose recomposes when values change
    private val _results = mutableStateMapOf<String, Any>()

    /**
     * Set a result to be consumed later.
     * @param key Unique identifier for the result.
     * @param value The data to pass. MUST be Parcelable, Serializable, or a primitive.
     */
    fun put(key: String, value: Any) {
        _results[key] = value
    }

    /**
     * Reads a result without removing it (useful for debugging, though rarely used in UI).
     */
    fun <T> get(key: String): T? {
        @Suppress("UNCHECKED_CAST")
        return _results[key] as? T
    }

    /**
     * Reads the result and immediately removes it from the store.
     * This is CRITICAL to prevent the result from re-triggering upon recomposition.
     */
    fun <T> consume(key: String): T? {
        val value = get<T>(key)
        _results.remove(key)
        return value
    }
}

@Composable
fun rememberResultStore(): ResultStore = remember { ResultStore() }
