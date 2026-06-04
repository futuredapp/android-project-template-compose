package app.futured.androidprojecttemplate.data.remote.result

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.util.reflect.TypeInfo
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.cast

@Singleton
class NetworkResultConverterFactory @Inject constructor(val errorParser: NetworkErrorParser) : Converter.Factory {

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit,
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if (typeData.typeInfo.type != NetworkResult::class) return null

        return object : Converter.SuspendResponseConverter<HttpResponse, Any> {

            override suspend fun convert(result: KtorfitResult): Any {
                val wrappedTypeInfo = typeData.typeArgs.first().typeInfo // NetworkResult<wrappedTypeInfo>

                return when (result) {
                    is KtorfitResult.Success -> result.response.toNetworkResult(expectedType = wrappedTypeInfo)
                    is KtorfitResult.Failure -> NetworkResult.error(errorParser.parse(result.throwable))
                }
            }
        }
    }

    private suspend inline fun HttpResponse.toNetworkResult(expectedType: TypeInfo): NetworkResult<Any> {
        if (!status.isSuccess()) {
            return NetworkResult.error(errorParser.parse(status))
        }

        return runCatching {
            NetworkResult.success(expectedType.type.cast(body(expectedType)))
        }.getOrElse { throwable ->
            NetworkResult.error(errorParser.parse(throwable))
        }
    }
}
