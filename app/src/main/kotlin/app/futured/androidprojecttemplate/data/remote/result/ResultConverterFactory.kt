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
class ResultConverterFactory @Inject constructor(val errorParser: NetworkErrorParser) : Converter.Factory {

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit,
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if (typeData.typeInfo.type != Result::class) return null

        return object : Converter.SuspendResponseConverter<HttpResponse, Any> {

            override suspend fun convert(result: KtorfitResult): Any {
                val wrappedTypeInfo = typeData.typeArgs.first().typeInfo // Result<wrappedTypeInfo>

                return when (result) {
                    is KtorfitResult.Success -> result.response.toResult(expectedType = wrappedTypeInfo)
                    is KtorfitResult.Failure -> Result.failure(errorParser.parse(result.throwable))
                }
            }
        }
    }

    private suspend inline fun HttpResponse.toResult(expectedType: TypeInfo): Result<Any> {
        if (!status.isSuccess()) {
            return Result.failure(errorParser.parse(status))
        }

        return runCatching {
            Result.success(expectedType.type.cast(body(expectedType)))
        }.getOrElse { throwable ->
            Result.failure(errorParser.parse(throwable))
        }
    }
}
