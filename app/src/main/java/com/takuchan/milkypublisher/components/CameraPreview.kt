package com.takuchan.milkypublisher.components

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.takuchan.milkypublisher.analysis.PoseCaptureImageAnalyzer
import com.takuchan.milkypublisher.background.getCameraProvider
import com.takuchan.milkypublisher.viewmodel.PoseDetectPointViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(
    cameraExecutorService: ExecutorService,
    poseDetectPointViewModel: PoseDetectPointViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    //Base pose detector with streaming frames

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val imageAnalyzer = ImageAnalysis.Builder()
            .build()

            .also {
                it.setAnalyzer(cameraExecutorService,PoseCaptureImageAnalyzer({ frameImage->
                    val mediaImage = frameImage.image

                },{poseLandmarks ->
                    poseDetectPointViewModel.addPoseDetectPointList(poseLandmarks)
                }))
            }
            // CameraX Preview UseCase
            val previewUseCase:Preview = Preview.Builder()
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
                        lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, previewUseCase,imageAnalyzer
                    )
                } catch (ex: Exception) {
                    Log.e("CameraPreview", "Use case binding failed", ex)
                }
            }
            previewView
        }
    )

}
