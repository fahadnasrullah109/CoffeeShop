package com.coffee.shop.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCE_NAME = "datastore_preferences"
private val Context.userPreferences by preferencesDataStore(
    name = PREFERENCE_NAME
)

class DatastorePreferences(private val context: Context) {

    val isIntroductionPresented: Flow<Boolean>
        get() = context.userPreferences.data.map { preferences ->
            preferences[KEY_IS_INTRODUCTION_PRESENTED] ?: false
        }

    suspend fun clear() {
        context.userPreferences.edit {
            it.clear()
        }
    }

    companion object {
        private val KEY_IS_INTRODUCTION_PRESENTED = booleanPreferencesKey("KEY_IS_INTRODUCTION_PRESENTED")
    }
}