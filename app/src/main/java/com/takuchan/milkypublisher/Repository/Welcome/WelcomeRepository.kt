package com.takuchan.milkypublisher.Repository.Welcome


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="settings")

class WelcomeRepository(
    private val context: Context
){
    private val is_launched_already = booleanPreferencesKey(name = "is_launched_already")

    val isFirstLaunch: Flow<Boolean> = context.dataStore.data
        .map{preferences ->
            preferences[is_launched_already] ?: true
        }

    suspend fun setFirstLaunchComplete(){ /* この関数が呼ばれたら二回目以降のようこそ画面は表示されない */
        context.dataStore.edit {preferences ->
            preferences[is_launched_already] = false
        }
    }
}