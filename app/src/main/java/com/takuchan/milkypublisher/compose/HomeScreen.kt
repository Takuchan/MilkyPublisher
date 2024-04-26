package com.takuchan.milkypublisher.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.takuchan.milkypublisher.components.CameraPreview
import com.takuchan.milkypublisher.model.LogScreenData
import com.takuchan.milkypublisher.model.LogScreenEnum
import com.takuchan.milkypublisher.viewmodel.DetectState
import com.takuchan.milkypublisher.viewmodel.LogViewModel
import java.util.Date
import java.util.concurrent.ExecutorService

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navMainController: NavController,
    navHomeController: NavController,
    modifier: Modifier = Modifier,
    detectState: DetectState,
    cameraExecutorService: ExecutorService,
    toBluetoothSettingButton: () -> Unit,
    logViewModel: LogViewModel
) {
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA,
    )



    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cameraPermissionState.status.isGranted) {

                CameraPreview(
                    cameraExecutorService = cameraExecutorService
                )

            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                        "本アプリはカメラ機能を使います。パーミッション許可を行ってください"
                    } else {
                        "本アプリを使用するためにはカメラのパーミッション許可が必須です。"
                    }
                    Text(textToShow)
                    Button(onClick = {
                        cameraPermissionState.launchPermissionRequest()
                        logViewModel.addLogScreenList(LogScreenData(Date(),LogScreenEnum.Application.name,"認証しました"))
                    }) {
                        Text("パーミッション許可")
                    }
                }
            }
        }


    }


}

