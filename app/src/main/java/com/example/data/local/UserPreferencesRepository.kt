package com.example.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "petroleum_settings")

data class UserPreferences(
    val language: AppLanguage = AppLanguage.EN,
    val autoRotate3d: Boolean = true,
    val show3dLabels: Boolean = true,
    val lowEndPerformanceMode: Boolean = false
)

class UserPreferencesRepository(private val context: Context) {
    private object Keys {
        val LANGUAGE = stringPreferencesKey("app_language")
        val AUTO_ROTATE_3D = booleanPreferencesKey("auto_rotate_3d")
        val SHOW_3D_LABELS = booleanPreferencesKey("show_3d_labels")
        val LOW_END_MODE = booleanPreferencesKey("low_end_performance_mode")
    }

    val preferencesFlow: Flow<UserPreferences> = context.dataStore.data.map { prefs ->
        val lang = if (prefs[Keys.LANGUAGE] == AppLanguage.UR.code) AppLanguage.UR else AppLanguage.EN
        UserPreferences(
            language = lang,
            autoRotate3d = prefs[Keys.AUTO_ROTATE_3D] ?: true,
            show3dLabels = prefs[Keys.SHOW_3D_LABELS] ?: true,
            lowEndPerformanceMode = prefs[Keys.LOW_END_MODE] ?: false
        )
    }

    suspend fun setLanguage(language: AppLanguage) {
        context.dataStore.edit { it[Keys.LANGUAGE] = language.code }
    }

    suspend fun setAutoRotate3d(enabled: Boolean) {
        context.dataStore.edit { it[Keys.AUTO_ROTATE_3D] = enabled }
    }

    suspend fun setShow3dLabels(enabled: Boolean) {
        context.dataStore.edit { it[Keys.SHOW_3D_LABELS] = enabled }
    }

    suspend fun setLowEndPerformanceMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.LOW_END_MODE] = enabled }
    }
}
