package app.futured.androidprojecttemplate.injection.modules

import android.content.Context
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.futured.androidprojecttemplate.tools.extensions.dataStore
import app.futured.androidprojecttemplate.tools.serialization.ZonedDateTimeSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.ZonedDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun resources(
        @ApplicationContext context: Context,
    ): Resources = context.resources

    @Provides
    fun json(): Json = Json(from = Json.Default) {
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
        serializersModule = SerializersModule {
            contextual(ZonedDateTime::class, ZonedDateTimeSerializer)
        }
    }

    @Provides
    fun dataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.dataStore
}
