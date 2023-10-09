package com.takuchan.milkypublisher


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.takuchan.milkypublisher.compose.HomeScreen
import com.takuchan.milkypublisher.ui.theme.MilkyPublisherTheme
import com.takuchan.milkypublisher.viewmodel.DetectState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private val viewModel: DetectState by viewModels()

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()

        //Bluetooth使用可能判別
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)){
//            Toast.makeText(applicationContext,"Bluetooth使える",Toast.LENGTH_SHORT).show()
        }


        GlobalScope.launch {
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                val deviceName = device.name
                val deviceHardwareAddress = device.address // MAC address
                Log.d("rered",deviceName)
            }
        }



        setContent {
            MilkyPublisherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MilkyPublisherApp(detectStateViewModel = viewModel, executorService = cameraExecutor)
                }
            }
        }
    }

}

@Composable
fun MilkyPublisherApp(detectStateViewModel: DetectState,executorService: ExecutorService){
    val navController = rememberNavController()
    MilkyPublisherNavHost(
        navController = navController,
        detectStateViewModel = detectStateViewModel,
        cameraExecutorService = executorService
        )
}

@Composable
fun MilkyPublisherNavHost(
    navController: NavHostController,
    detectStateViewModel: DetectState,
    cameraExecutorService: ExecutorService
){

    NavHost(navController = navController, startDestination = "home"){
        composable("home") {
            HomeScreen(navController, detectState = detectStateViewModel, cameraExecutorService = cameraExecutorService)
        }
    }
}