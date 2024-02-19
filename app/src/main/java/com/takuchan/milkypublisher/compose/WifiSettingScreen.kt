package com.takuchan.milkypublisher.compose

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController
import com.takuchan.milkypublisher.compose.utils.wifiListCard
import com.takuchan.milkypublisher.modifiernode.paddingSpaceLeftRight
import com.takuchan.milkypublisher.modifiernode.paddingSpaceUpandDown

import com.takuchan.milkypublisher.compose.utils.wifiListCard

import com.takuchan.milkypublisher.preference.LocalNetworkDetail

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WifiSettingScreen(
    navController: NavController
) {
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
                        Text(text = "192.0.0.1", style = MaterialTheme.typography.headlineSmall)
                        Text(text = "ポート番号")
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

    }
}

