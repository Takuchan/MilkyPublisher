package com.takuchan.milkypublisher.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.takuchan.milkypublisher.components.CameraPreview
import com.takuchan.milkypublisher.compose.utils.ReadyButton
import com.takuchan.milkypublisher.viewmodel.DetectState
import java.util.concurrent.ExecutorService

@OptIn(ExperimentalMaterial3Api::class,ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    detectState: DetectState,
    cameraExecutorService: ExecutorService,
    toBluetoothSettingButton: ()->Unit
) {
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA,
    )


    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(cameraPermissionState.status.isGranted){
                TopAppBar(
                    title = { Text("MilkyPublisher") }
                )
                Spacer(modifier = Modifier.weight(1f))
                Column(modifier = Modifier.padding(12.dp)) {
                    ReadyButton(
                        modifier = Modifier,
                        viewModel = detectState,
                        onClick = {
                            detectState.currentStateToggle()
                        })
                }
                CameraPreview(cameraExecutorService = cameraExecutorService)

                Spacer(modifier = Modifier.weight(1f))
            }else{
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val textToShow = if (cameraPermissionState.status.shouldShowRationale){
                        "本アプリはカメラ機能を使います。パーミッション許可を行ってください"
                    }else{
                        "本アプリを使用するためにはカメラのパーミッション許可が必須です。"
                    }
                    Text(textToShow)
                    Button(onClick = {
                        cameraPermissionState.launchPermissionRequest()
                    }) {
                        Text("パーミッション許可")
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp),
            onClick = {
                //設定画面に画面遷移
                toBluetoothSettingButton()
            }
        ) {
            Text(text = "Bluetoothの接続先がありません")
        }
    }

}
