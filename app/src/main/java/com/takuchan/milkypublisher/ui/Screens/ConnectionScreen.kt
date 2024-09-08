package com.takuchan.milkypublisher.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.takuchan.milkypublisher.R

@Composable
fun ROS2NetworkSettingsScreen() {
    var ipAddress by remember { mutableStateOf("") }
    var domainId by remember { mutableStateOf("") }
    var port by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MilkyPublisher × ROS2",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        InputField(value = ipAddress, onValueChange = { ipAddress = it }, label = "IPアドレス")
        InputField(value = domainId, onValueChange = { domainId = it }, label = "DOMAIN ID")
        InputField(value = port, onValueChange = { port = it }, label = "PORT番号")

        Image(
            painter = painterResource(id = R.drawable.ros2_tm_color),
            contentDescription = "ROS2 Logo",
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = "本アプリはROS2およびFastDDSプロトコルを使用します。",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = { /* 接続処理を実装 */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("接続")
        }
    }
}

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        singleLine = true
    )
}

@Preview
@Composable
fun ROS2NetworkSettingsScreenPreview() {
    ROS2NetworkSettingsScreen()
}