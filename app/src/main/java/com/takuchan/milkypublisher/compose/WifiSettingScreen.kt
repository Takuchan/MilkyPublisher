package com.takuchan.milkypublisher.compose

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.takuchan.milkypublisher.compose.utils.wifiListCard
import com.takuchan.milkypublisher.preference.LocalNetworkDetail

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WifiSettingScreen(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Wifi接続先設定") }) },
    ) {
        Column(
            modifier= Modifier.padding(it)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                    ) {
                        Text(text = "192.0.0.1",style = MaterialTheme.typography.headlineLarge)
                        Text(text = "ポート番号")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { /*TODO*/ }) {
                        Text("接続")
                    }
                }
            }
            val devices = LocalNetworkDetail()

            devices.getDeviceInNetowrk().forEach { device ->
                wifiListCard(
                    wifiname = device.name,
                    wifiIP = device.ip
                )
            }
        }
    }
}

