package com.takuchan.milkypublisher.compose

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.takuchan.milkypublisher.viewmodel.ConnectingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WifiSettingScreen(
    showDialog: ConnectingViewModel
){
    val wifiPhrase1 = remember { mutableStateOf(TextFieldValue("")) }
    val wifiPhrase2 = remember { mutableStateOf(TextFieldValue("")) }
    val wifiPhrase3 = remember { mutableStateOf(TextFieldValue("")) }
    val wifiPhrase4 = remember { mutableStateOf(TextFieldValue("")) }

    val wifiPort = remember { mutableStateOf(TextFieldValue(""))}


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
                Text("Wifiで設定する場合はMilkyPublisherの制御を行うパソコンのIPv4アドレスを入力します")

                Row(modifier = Modifier.fillMaxWidth()) {

                    OutlinedTextField(value = wifiPhrase1.value,
                        onValueChange = { wifiPhrase1.value = it },
                        label = { Text("") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Text(".",modifier = Modifier.align(Alignment.Bottom), fontSize = 40.sp)

                    OutlinedTextField(value = wifiPhrase2.value,
                        onValueChange = { wifiPhrase2.value = it },
                        label = { Text("") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    Text(".",modifier = Modifier.align(Alignment.Bottom), fontSize = 40.sp)

                    OutlinedTextField(value = wifiPhrase3.value,
                        onValueChange = { wifiPhrase3.value = it },
                        label = { Text("") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    Text(".",modifier = Modifier.align(Alignment.Bottom), fontSize = 40.sp)
                    OutlinedTextField(value = wifiPhrase4.value,
                        onValueChange = { wifiPhrase4.value = it },
                        label = { Text("") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

                }
                OutlinedTextField(value = wifiPort.value, onValueChange = {
                    wifiPort.value =it
                },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("ポート番号") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            }

        }
    )

}