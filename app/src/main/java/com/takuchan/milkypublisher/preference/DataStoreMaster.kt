package com.takuchan.milkypublisher.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreMaster(private val context: Context){
    private val Context.signalsSetting: DataStore<Preferences> by preferencesDataStore(name = "signals_setting")

    companion object{
        private val wifiIPKey = stringPreferencesKey("wifi_ips")
        private val wifiPortKey = stringPreferencesKey("wifi_port")

    }

    val getIpv4Address: Flow<String> = context.signalsSetting.data.map { preferences ->
        preferences[wifiIPKey] ?: "0.0.0.0"
    }
    val getWifiPort: Flow<String> = context.signalsSetting.data.map { preferences ->
        preferences[wifiPortKey] ?: "0"
    }

    suspend fun saveIpv4Address(ipv4Address: String){
        context.signalsSetting.edit { preferences ->
            preferences[wifiIPKey] = ipv4Address
        }
    }

    suspend fun saveWifiPort(port: String) {
        context.signalsSetting.edit { preferences ->
            preferences[wifiPortKey] = port
        }
    }

}