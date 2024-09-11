package com.takuchan.milkypublisher.ui.Screens

import androidx.compose.runtime.Composable
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.ExecutorService
import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.pose.PoseLandmark
import com.takuchan.milkypublisher.Model.getCameraProvider
import com.takuchan.milkypublisher.data.analyzer.PoseCaptureImageAnalyzer
import timber.log.Timber

// widthとheightを格納するdata class
data class PreviewScreenSize(
    val width: Float,
    val height: Float
)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreview(
    executor: ExecutorService,
    screenSize: PreviewScreenSize
) {
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA,
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    var previewView by remember { mutableStateOf<PreviewView?>(null) }
    var detectedPose by remember { mutableStateOf<List<PoseLandmark>>(emptyList()) }
    var canvasSize by remember { mutableStateOf(screenSize) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    this.scaleType = PreviewView.ScaleType.FIT_CENTER
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }

                val poseAnalyzer = ImageAnalysis.Builder()
                    .build()
                    .also{
                        it.setAnalyzer(
                            executor,
                            PoseCaptureImageAnalyzer({ detectedState ->


                            }, { poseLandmarks ->

                            })
                        )
                    }

                // CameraX Preview UseCase
                val previewUseCase: Preview = Preview.Builder()
                    .setTargetResolution(
                        android.util.Size(480, 640)
                    )
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                coroutineScope.launch {
                    val cameraProvider = context.getCameraProvider()
                    try {
                        // use caseをライフサイクルにバインドする前にアンバインドを行う必要がある
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            previewUseCase,
                            poseAnalyzer
                        )


                    } catch (ex: Exception) {
                        Timber.e(ex)
                    }
                }
                previewView
            }
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize() // Canvasのサイズを指定されたサイズに設定
                .border(1.dp, Color.Green)
        ) {
            Timber.i(detectedPose.size.toString())
            detectedPose.forEach { landmark ->
                drawCircle(
                    color = Color.Red,
                    center = Offset(
                        landmark.position.x * (size.width / canvasSize.width ) ,
                        landmark.position.y * (size.height / canvasSize.height)
                    ),
                    radius = 5f
                )
            }
        }
    }

    LaunchedEffect(previewView) {
        if(!cameraPermissionState.status.isGranted){
            cameraPermissionState.launchPermissionRequest()
        }

    }
}