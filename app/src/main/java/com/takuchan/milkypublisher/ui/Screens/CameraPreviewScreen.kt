package com.takuchan.milkypublisher.ui.Screens

import android.util.Log
import androidx.compose.runtime.Composable
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.pose.PoseLandmark
import com.takuchan.milkypublisher.Model.getCameraProvider
import com.takuchan.milkypublisher.ViewModel.publisherscreen.PublisherScreenViewModel
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
    screenSize: PreviewScreenSize = PreviewScreenSize(640F, 480F),
    viewModel: PublisherScreenViewModel = hiltViewModel()
) {
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA,
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    var previewView by remember { mutableStateOf<PreviewView?>(null) }
    var canvasSize by remember { mutableStateOf(screenSize) }

    val uiState by viewModel.uiState.collectAsState()

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
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                val previewUseCase: Preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(executor) { imageProxy ->
                    viewModel.processImage(imageProxy)
                }

                coroutineScope.launch {
                    val cameraProvider = context.getCameraProvider()
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            previewUseCase,
                            imageAnalysis
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
                .fillMaxSize()
        ) {
            val scaleX = size.width / screenSize.width
            val scaleY = size.height / screenSize.height

            // Draw pose landmarks
            if (uiState.publisherDetectionState.isDetectPose) {
                uiState.poseLandmarks.forEach { landmark ->
                    drawCircle(
                        color = Color.Red,
                        center = Offset(
                            landmark.position.x * scaleX,
                            landmark.position.y * scaleY
                        ),
                        radius = 5f
                    )
                }
            }

            // Draw face landmarks
            if (uiState.publisherDetectionState.isDetectFace) {
                uiState.faceLandmarks.forEach { face ->
                    face.forEach { landmark ->
                        drawCircle(
                            color = Color.Blue,
                            center = Offset(
                                landmark.position.x * scaleX,
                                landmark.position.y * scaleY
                            ),
                            radius = 3f
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(previewView) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}