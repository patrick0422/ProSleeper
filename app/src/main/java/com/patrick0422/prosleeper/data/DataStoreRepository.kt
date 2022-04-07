package com.patrick0422.prosleeper.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.patrick0422.prosleeper.util.Constants.DATASTORE_NAME
import com.patrick0422.prosleeper.util.Constants.IS_NOTIFICATION_ALLOWED
import com.patrick0422.prosleeper.util.Constants.NOTIFICATION_TIME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferenceKeys {
        val isNotificationAllowed = booleanPreferencesKey(IS_NOTIFICATION_ALLOWED)
        val notificationTime = stringPreferencesKey(NOTIFICATION_TIME)
    }

    val readIsNotificationAllowed: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }
        .map { preferences ->
            preferences[PreferenceKeys.isNotificationAllowed] ?: false
        }

    val readNotificationTime: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }
        .map { preferences ->
            preferences[PreferenceKeys.notificationTime] ?: "07:00"
        }

    suspend fun saveIsNotificationAllowed(isAllowed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.isNotificationAllowed] = isAllowed
        }
    }

    suspend fun saveNotificationTime(notificationTime: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.notificationTime] = notificationTime
        }
    }
}