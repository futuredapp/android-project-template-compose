package app.futured.androidprojecttemplate.tools.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import app.futured.androidprojecttemplate.tools.Constants

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DataStore.DEFAULT_DATASTORE_NAME)
