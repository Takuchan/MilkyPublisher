package com.takuchan.milkypublisher.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import java.net.NetworkInterface

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConnectingScreen(
    navController: NavController
) {
    var ipv4Address by remember { mutableStateOf("") }

    var showWifiDialog by remember { mutableStateOf(false) }
    var showBluetoothDialog by remember { mutableStateOf(false) }
    var showCableDialog by remember { mutableStateOf(false) }

    //Dialogからゲットしたデータを格納するリスト
    var connecting2IpAddr by remember{ mutableStateOf("") }


    //Dialogの変数の中身を取得
    var wifiphrase1 by remember { mutableIntStateOf(0) }
    var wifiphrase2 by remember { mutableIntStateOf(0) }
    var wifiphrase3 by remember { mutableIntStateOf(0) }
    var wifiphrase4 by remember { mutableIntStateOf(0) }




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
        Column(
            modifier = Modifier.padding(16.dp)
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
                        showWifiDialog = true

                    }) {
                        Icon(Icons.Filled.ArrowForward,contentDescription = "Wifi設定")
                    }


                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Text("Bluetooh",style = MaterialTheme.typography.headlineSmall)
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

    }
    if(showWifiDialog){

        AlertDialog(
            onDismissRequest = {
                               showWifiDialog = false
            },
            confirmButton ={
                           TextButton(onClick = {
                               showWifiDialog = false
                           }) {
                               Text(text = "OK")
                           }

            },
            dismissButton = {
                TextButton(onClick = {
                    showWifiDialog = false
                }) {
                    Text("キャンセル")
                }
            },
            title = {
                Text(text = "Wifi接続先設定")
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Wifiに設定する場合はROS制御を行うパソコンのIPv4アドレスを入力します")
                    Row {

                    }
                }
            }
        )
    }
    if(showBluetoothDialog){

    }


}

