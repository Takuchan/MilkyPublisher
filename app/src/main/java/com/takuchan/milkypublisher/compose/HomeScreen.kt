package com.takuchan.milkypublisher.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.takuchan.milkypublisher.components.CameraPreview
import com.takuchan.milkypublisher.compose.utils.ReadyButton
import com.takuchan.milkypublisher.viewmodel.DetectState

@OptIn(ExperimentalMaterial3Api::class,ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    detectState: DetectState
) {
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    val nowDetection by detectState.currentState.collectAsState()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(cameraPermissionState.status.isGranted){
            TopAppBar(
                title = { Text("MilkyPublisher") },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Open drawer")
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Filled.Build, contentDescription = "Edit text")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Filled.Share, contentDescription = "Share text")
                    }
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            CameraPreview()
            ReadyButton(
                modifier = modifier,
                viewModel = detectState,
                onClick = {
                    detectState.currentStateToggle()
                })
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
}
