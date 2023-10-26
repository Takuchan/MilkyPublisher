package com.takuchan.milkypublisher


import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.takuchan.milkypublisher.compose.BluetoothSettingScreen

import com.takuchan.milkypublisher.compose.HomeScreen
import com.takuchan.milkypublisher.model.BluetoothNowState
import com.takuchan.milkypublisher.ui.theme.MilkyPublisherTheme
import com.takuchan.milkypublisher.viewmodel.DetectBluetoothList
import com.takuchan.milkypublisher.viewmodel.DetectState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private val viewModel: DetectState by viewModels()
    private lateinit var cameraExecutor: ExecutorService

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        val blViewModel = ViewModelProvider(this)[DetectBluetoothList::class.java]

        //Bluetooth使用可能判別
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)){
//            Toast.makeText(applicationContext,"Bluetooth使える",Toast.LENGTH_SHORT).show()
        }

        setContent {
            MilkyPublisherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MilkyPublisherApp(detectStateViewModel = viewModel, executorService = cameraExecutor,blViewModel = blViewModel)
                }
            }
        }
    }

}

@Composable
fun MilkyPublisherApp(detectStateViewModel: DetectState,executorService: ExecutorService,blViewModel: DetectBluetoothList){
    val navController = rememberNavController()
    MilkyPublisherNavHost(
        navController = navController,
        detectStateViewModel = detectStateViewModel,
        cameraExecutorService = executorService,
        blViewModel = blViewModel,
        )
}

@Composable
fun MilkyPublisherNavHost(
    navController: NavHostController,
    detectStateViewModel: DetectState,
    cameraExecutorService: ExecutorService,
    blViewModel:DetectBluetoothList,
){

    NavHost(navController = navController, startDestination = "home"){
        composable("home") {
            HomeScreen(
                navController,
                detectState = detectStateViewModel,
                cameraExecutorService = cameraExecutorService,
                toBluetoothSettingButton = {
                    navController.navigate("bluetoothSetting")
                })
        }
        composable("bluetoothSetting"){
            BluetoothSettingScreen(navController = navController, blViewModel =blViewModel)
        }
    }
}