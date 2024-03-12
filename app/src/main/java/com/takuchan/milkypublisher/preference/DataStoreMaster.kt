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
import com.takuchan.milkypublisher.enums.ConnectingEnum
import com.takuchan.milkypublisher.enums.DataStoreKeysEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreMaster(private val context: Context){
    private val Context.signalsSetting: DataStore<Preferences> by preferencesDataStore(name = "signals_setting")

    companion object{
        //接続系の情報を保存するためのDataStore
        private val wifiIPKey = stringPreferencesKey(DataStoreKeysEnum.wifiIPKey.name)
        private val wifiPortKey = stringPreferencesKey(DataStoreKeysEnum.wifiPortKey.name)
        private val connectingStates = stringPreferencesKey(DataStoreKeysEnum.connectingState.name)
    }

    val getNetInfo: Flow<Preferences> = context.signalsSetting.data.map { preferences ->
        preferences
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

    suspend fun setAccessPoint(data: String){
            context.signalsSetting.edit { preferences ->
            preferences[connectingStates] = data
        }
    }
}