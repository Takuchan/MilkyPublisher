package com.takuchan.milkypublisher.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.takuchan.milkypublisher.viewmodel.ConnectingViewModel

import java.net.NetworkInterface

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConnectingScreen(
    onPrevButtonClicked: () -> Unit,
    navController: NavController,
    connectingViewModel: ConnectingViewModel = viewModel()
) {
    var ipv4Address by remember { mutableStateOf("") }


    //Dialogからゲットしたデータを格納するリスト
    var connecting2IpAddr by remember{ mutableStateOf("") }


    //ConnectScreen専用のViewModelを初期化
    val showWifiDialog by connectingViewModel.showWifiDialog.observeAsState(false)
    val showBluetoothDialog by connectingViewModel.showBlueToothDialog.observeAsState(false)
    val showWiredDialog by connectingViewModel.showDialog.observeAsState(false)



    LaunchedEffect(key1 = Unit) {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val inetAddresses = networkInterface.inetAddresses
            while (inetAddresses.hasMoreElements()) {
                val inetAddress = inetAddresses.nextElement()
                if (!inetAddress.isLoopbackAddress && inetAddress.hostAddress.contains(":").not()) {
                    ipv4Address = inetAddress.hostAddress
                    return@LaunchedEffect
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("接続先設定") },
                navigationIcon = {
                    IconButton(onClick = { onPrevButtonClicked() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it).padding(16.dp)
        ) {
            Text("接続先は一個のみです。")
            Spacer(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
            Text("Wi-Fi",style = MaterialTheme.typography.headlineSmall)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                    ) {
                        Text(text = connecting2IpAddr, style = MaterialTheme.typography.headlineSmall)
                        Text(text = "ポート番号")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { /*TODO*/ }) {
                        Text("接続")
                    }

                    IconButton(onClick = {
                        //Wifi のボタンを押したときの操作を行う
                        connectingViewModel.setShowWifiDialog(true)

                    }) {
                        Icon(Icons.Filled.ArrowForward,contentDescription = "Wifi設定")
                    }


                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Text("Bluetooth",style = MaterialTheme.typography.headlineSmall)
            Card(
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                    ) {
                        Text(text = "Bluetooth名", style = MaterialTheme.typography.titleLarge)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { /*TODO*/ }) {
                        Text("接続")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.ArrowForward,contentDescription = "Wifi設定")
                    }
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Text("有線", style = MaterialTheme.typography.headlineSmall)
            Card(
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    modifier = Modifier.padding(16.dp)
                ){
                    Column {
                        Text("接続先PC名",style = MaterialTheme.typography.titleLarge)

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { /*TODO*/ }) {
                        Text("接続")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.ArrowForward,contentDescription = "Wifi設定")
                    }
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Text("端末のIPアドレス$ipv4Address")

            if(showWifiDialog){
                //Wifi設定を行うときのアラートダイアログ
                WifiSettingScreen(showDialog = connectingViewModel)
            }

        }

    }


}

