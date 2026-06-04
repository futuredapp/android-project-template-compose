package app.futured.androidprojecttemplate.data.persistence

import androidx.datastore.preferences.core.booleanPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPersistence @Inject constructor(private val persistence: PrimitivePersistence) {

    suspend fun setUserLoggedIn(isLoggedIn: Boolean) {
        persistence.save(IS_USER_LOGGED_IN_KEY, isLoggedIn)
    }

    suspend fun isUserLoggedIn(): Boolean = persistence.get(IS_USER_LOGGED_IN_KEY) ?: false

    companion object {
        private val IS_USER_LOGGED_IN_KEY = booleanPreferencesKey("IS_USER_LOGGED_IN")
    }
}
