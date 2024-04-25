package com.takuchan.milkypublisher


import android.annotation.SuppressLint
import android.content.pm.PackageManager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.takuchan.milkypublisher.compose.BluetoothSettingScreen
import com.takuchan.milkypublisher.compose.ConnectingScreen

import com.takuchan.milkypublisher.compose.MainScreen
import com.takuchan.milkypublisher.enums.DataStoreKeysEnum
import com.takuchan.milkypublisher.preference.DataStoreMaster

import com.takuchan.milkypublisher.ui.theme.MilkyPublisherTheme
import com.takuchan.milkypublisher.viewmodel.ConnectingViewModel
import com.takuchan.milkypublisher.viewmodel.DetectBluetoothList
import com.takuchan.milkypublisher.viewmodel.DetectState
import com.takuchan.milkypublisher.viewmodel.UDPFlowViewModel
import kotlinx.coroutines.launch


import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private val viewModel: DetectState by viewModels()
    private lateinit var cameraExecutor: ExecutorService

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("MissingPermission", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        val blViewModel = ViewModelProvider(this)[DetectBluetoothList::class.java]
        val udpViewModel = ViewModelProvider(this)[UDPFlowViewModel::class.java]
        val connectingViewModel = ViewModelProvider(this)[ConnectingViewModel::class.java]
        val datastore: DataStoreMaster = DataStoreMaster(this)

        lifecycleScope.launch {
            datastore.getNetInfo.collect{preferences ->
                Log.d("MainAcitivty.kt", "$preferences")
                val ipAddr = preferences[stringPreferencesKey(DataStoreKeysEnum.wifiIPKey.name)]
                val port = preferences[stringPreferencesKey(DataStoreKeysEnum.wifiPortKey.name)]
                val settingState = preferences[stringPreferencesKey(DataStoreKeysEnum.connectingState.name)]
                connectingViewModel.setWifiIpAddr(ipAddr ?: "0.0.0.0")
                connectingViewModel.setWifiPort(port ?: "4001")
                connectingViewModel.setConnectingStatus(settingState?: "None")
            }
        }


        if (packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
//            Toast.makeText(applicationContext,"Bluetooth使える",Toast.LENGTH_SHORT).show()
        }

        setContent {
            MilkyPublisherTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    MilkyPublisherApp(
                        detectStateViewModel = viewModel,
                        executorService = cameraExecutor,
                        blViewModel = blViewModel,
                        udpViewModel = udpViewModel,
                        connectingViewModel = connectingViewModel,
                        dataStoreMaster = datastore
                    )
                }
            }
        }
    }

}

@Composable
fun MilkyPublisherApp(
    detectStateViewModel: DetectState,
    executorService: ExecutorService,
    blViewModel: DetectBluetoothList,
    udpViewModel: UDPFlowViewModel,
    connectingViewModel: ConnectingViewModel,
    dataStoreMaster: DataStoreMaster
) {
    val navController = rememberNavController()
    val lifecycleOwner = LocalLifecycleOwner.current

    udpViewModel.receiveUDP.observe(lifecycleOwner, Observer { newData ->
        Log.d("UDPしたよ", newData)
    })
    MilkyPublisherNavHost(
        navController = navController,
        detectStateViewModel = detectStateViewModel,
        cameraExecutorService = executorService,
        blViewModel = blViewModel,
        connectingViewModel = connectingViewModel,
        dataStoreMaster = dataStoreMaster
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MilkyPublisherNavHost(
    navController: NavHostController,
    detectStateViewModel: DetectState,
    cameraExecutorService: ExecutorService,
    blViewModel: DetectBluetoothList,
    connectingViewModel: ConnectingViewModel,
    dataStoreMaster: DataStoreMaster
) {

    Column {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                MainScreen(
                    navMainController = navController,
                    detectState = detectStateViewModel,
                    cameraExecutorService = cameraExecutorService,
                    connectingViewModel = connectingViewModel,
                    toBluetoothSettingButton = {
                        navController.navigate("wifiSetting")
                    })
            }
            composable("bluetoothSetting") {
                BluetoothSettingScreen(navController = navController, blViewModel = blViewModel)
            }
            composable("wifiSetting") {
                ConnectingScreen(
                    {
                        navController.popBackStack()
                    },
                    navController = navController,
                    connectingViewModel = connectingViewModel,
                    dataStoreMaster = dataStoreMaster
                )
            }
        }


    }


}