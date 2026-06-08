package com.nursing.admin.mobile.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class TokenManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val ADMIN_ID_KEY = stringPreferencesKey("admin_id")
        private val ADMIN_EMAIL_KEY = stringPreferencesKey("admin_email")
        private val ADMIN_NAME_KEY = stringPreferencesKey("admin_name")
    }

    fun getToken(): String? {
        // This is a blocking call - use Flow for reactive approach
        return null
    }

    fun getTokenFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveAdminInfo(adminId: String, email: String, name: String) {
        context.dataStore.edit { preferences ->
            preferences[ADMIN_ID_KEY] = adminId
            preferences[ADMIN_EMAIL_KEY] = email
            preferences[ADMIN_NAME_KEY] = name
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun getAdminIdFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ADMIN_ID_KEY]
        }
    }

    fun getAdminEmailFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ADMIN_EMAIL_KEY]
        }
    }

    fun getAdminNameFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ADMIN_NAME_KEY]
        }
    }
}
