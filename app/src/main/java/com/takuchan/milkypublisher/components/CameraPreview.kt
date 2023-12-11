package com.takuchan.milkypublisher.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.nfc.Tag
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
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.takuchan.milkypublisher.analysis.CaptureImageAnalyzer
import com.takuchan.milkypublisher.background.getCameraProvider
import com.takuchan.milkypublisher.preference.UDPController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(
    cameraExecutorService: ExecutorService
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


            // 骨格検知をしたものをGraphicOverlayで表示する



            val imageAnalyzer = ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(cameraExecutorService,CaptureImageAnalyzer{frameImage->
//                    val image =
//                        frameImage.image?.let { it1 -> InputImage.fromMediaImage(it1,frameImage.imageInfo.rotationDegrees) }
                    val mediaImage = frameImage.image
                    Log.d("TAG","mediaImage: $mediaImage")

                    if (mediaImage != null) {
                        val image = InputImage.fromMediaImage(
                            mediaImage,
                            frameImage.imageInfo.rotationDegrees
                        )
                        Log.d("TAAG","mediaImage: $image")

                        val options = PoseDetectorOptions.Builder()
                            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
                            .build()
                        val poseDetector = PoseDetection.getClient(options)

                        val poseDetectorTask: Task<Pose> = poseDetector.process(image)

                        poseDetectorTask.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val pose = task.result
                                Log.d("PoseDetect","姿勢検出成功")
                                val allPoseLandmarks = pose.allPoseLandmarks
                                for (landmark in allPoseLandmarks) {
                                    val landmarkName = landmark.landmarkType
                                    val landmarkPoint = landmark.position
                                    Log.d("PoseDetect", "landmarkName: $landmarkName")
                                    Log.d("PoseDetect", "landmarkPoint: $landmarkPoint")

                                }
                                GlobalScope.launch {
                                    // ここでWifiのUDPを処理させる
                                    UDPController().send(pose)
                                }
                                // Task completed successfully
                                // ...
                            } else {
                                val e = task.exception
                                // Task failed with an exception
                                // ...
                            }
                        }
                    }
                })
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


