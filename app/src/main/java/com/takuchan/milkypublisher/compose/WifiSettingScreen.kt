package com.takuchan.milkypublisher.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.takuchan.milkypublisher.viewmodel.ConnectingViewModel


@Composable
fun WifiSettingScreen(
    showDialog: ConnectingViewModel
){
    var wifiphrase1 by remember { mutableIntStateOf(0) }
    var wifiphrase2 by remember { mutableIntStateOf(0) }
    var wifiphrase3 by remember { mutableIntStateOf(0) }
    var wifiphrase4 by remember { mutableIntStateOf(0) }

    AlertDialog(
        onDismissRequest = {
            showDialog.setShowWifiDialog(false)
        },
        confirmButton ={
            TextButton(onClick = {
                showDialog.setShowWifiDialog(false)
            }) {
                Text(text = "OK")
            }

        },
        dismissButton = {
            TextButton(onClick = {
                showDialog.setShowWifiDialog(false)
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