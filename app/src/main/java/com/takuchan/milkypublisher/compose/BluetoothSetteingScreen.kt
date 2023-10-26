package com.takuchan.milkypublisher.compose

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.takuchan.milkypublisher.background.ObserveLifecycleEvent
import com.takuchan.milkypublisher.model.BluetoothNowState
import com.takuchan.milkypublisher.viewmodel.DetectBluetoothList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MissingPermission")
@OptIn(ExperimentalMaterial3Api::class,ExperimentalPermissionsApi::class)
@Composable
fun BluetoothSettingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    blViewModel: DetectBluetoothList = viewModel(),
) {
    val context = LocalContext.current
    ObserveLifecycleEvent { event ->
        // 検出したイベントに応じた処理を実装する。
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                runBlocking {
                    withContext(Dispatchers.IO){
                        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
                        val bluetoothAdapter = bluetoothManager.adapter
                        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
                        pairedDevices?.forEach { device ->
                            val deviceName = device.name
                            val deviceHardwareAddress = device.address
                            val nowbluetoothstate = BluetoothNowState(deviceName,deviceHardwareAddress)
                            blViewModel.addBluetoothList(nowbluetoothstate)
                        }
                    }
                }
            }
            Lifecycle.Event.ON_RESUME -> Log.d("LifecycleEventSample", "On Resumeしたよ")
            Lifecycle.Event.ON_PAUSE -> Log.d("LifecycleEventSample", "On Pause")
            else -> {}
        }
    }
    val bluetoothList by blViewModel.bluetoothList.observeAsState(initial = mutableListOf())


    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Bluetooth接続先一覧") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back screen"
                        )
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS)
                    context.startActivity(intent)
                    Toast.makeText(context,"接続先の端末とペアリングしてください。",Toast.LENGTH_LONG).show()
                },
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {


        LazyColumn {
            items(bluetoothList){ device ->
                Text(device.name)
            }
        }
    }
}