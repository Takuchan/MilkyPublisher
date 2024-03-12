package com.takuchan.milkypublisher.preference
import kotlin.collections.Map
import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.takuchan.milkypublisher.enums.DataStoreKeysEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreMaster(private val context: Context){
    private val Context.signalsSetting: DataStore<Preferences> by preferencesDataStore(name = "signals_setting")

    companion object{
        private val wifiIPKey = stringPreferencesKey(DataStoreKeysEnum.wifiIPKey.name)
        private val wifiPortKey = stringPreferencesKey(DataStoreKeysEnum.wifiPortKey.name)
    }

    val getNetInfo: Flow<Preferences> = context.signalsSetting.data.map { preferences ->
        preferences
    }

    val getIpPort: Flow<String> = context.signalsSetting.data.map { preferences ->
        preferences[wifiPortKey] ?: "4001"
    }

    suspend fun saveIpv4Address(ipv4Address: String){
        context.signalsSetting.edit { preferences ->
            preferences[wifiIPKey] = ipv4Address
        }
    }
    suspend fun saveIpPort(ipport: String){
        context.signalsSetting.edit { preferences ->
            preferences[wifiPortKey] = ipport
        }
    }

}