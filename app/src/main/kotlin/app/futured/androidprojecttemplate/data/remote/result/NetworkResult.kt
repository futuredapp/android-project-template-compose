package app.futured.androidprojecttemplate.data.remote.result

/**
 * Wrapper class with either [Success] or [Failure] state.
 * The class is used as a result of network operations.
 */
sealed class NetworkResult<out T> {

    /**
     * The success result with [data] as the response of the operation.
     */
    data class Success<T>(val data: T) : NetworkResult<T>()

    /**
     * The failed result with [error] as the failure cause of the operation.
     */
    data class Failure(val error: NetworkError) : NetworkResult<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(error: NetworkError) = Failure(error)
    }
}

/**
 * Returns the encapsulated value if this instance represents [NetworkResult.Success] or
 * throws the encapsulated [Throwable] exception if it is [NetworkResult.Failure].
 */
inline fun <reified T> NetworkResult<T>.getOrThrow(): T = when (this) {
    is NetworkResult.Success -> data
    is NetworkResult.Failure -> throw error
}
