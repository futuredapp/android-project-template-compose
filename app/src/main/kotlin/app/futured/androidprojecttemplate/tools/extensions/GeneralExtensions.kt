package app.futured.androidprojecttemplate.tools.extensions

inline fun <T> T?.ifNull(defaultValue: () -> T): T = this ?: defaultValue.invoke()

inline fun <T> withValue(receiver: T, block: T.() -> Unit) {
    receiver.block()
}

inline fun <reified T : Any> withNonNullValue(receiver: T?, block: (T) -> Unit) {
    if (receiver != null) block(receiver)
}

inline fun <T> safe(block: () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T> T?.orThrow(): T = this ?: error("UnexpectedError") // Dev error

inline fun <reified T> ifElseNull(predicate: Boolean, block: () -> T?) = if (predicate) block.invoke() else null

inline fun <T> T.runWith(block: (T) -> Unit) {
    block(this)
}
