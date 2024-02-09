package com.takuchan.milkypublisher.components

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.mlkit.vision.pose.PoseLandmark
import com.takuchan.milkypublisher.analysis.PoseCaptureImageAnalyzer
import com.takuchan.milkypublisher.background.getCameraProvider
import com.takuchan.milkypublisher.compose.utils.LogPreView
import com.takuchan.milkypublisher.model.DetectTypeEnum
import com.takuchan.milkypublisher.model.LogData
import com.takuchan.milkypublisher.viewmodel.LogViewModel
import com.takuchan.milkypublisher.viewmodel.PoseDetectPointViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.ExecutorService

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(
    cameraExecutorService: ExecutorService,
    poseDetectPointViewModel: PoseDetectPointViewModel = viewModel(),
    logViewModel: LogViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val posedata by poseDetectPointViewModel.poseDetectPointList.observeAsState()

    //CameraPreview関数の縦横の幅の大きさを取得
//    var width by remember { mutableIntStateOf(0) }
//    var height by remember { mutableIntStateOf(0) }
    var size by remember { mutableStateOf(Size.Zero) }
    //Base pose detector with streaming frames
    Box(modifier = Modifier.fillMaxSize()){
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { layoutCoordinates ->
                    size = layoutCoordinates.size.toSize()
                },
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
                        it.setAnalyzer(cameraExecutorService,PoseCaptureImageAnalyzer({ detectedState ->
                            logViewModel.addLogList(
                                LogData(detectType = DetectTypeEnum.PoseDetection,detectState = detectedState, detectTime = Date(),detectData= ""
                                ))
                        },{poseLandmarks ->
                            Log.d("size","${size.width} : ${size.height}")
                            poseDetectPointViewModel.addPoseDetectPointList(poseLandmarks)
                            for (i in poseLandmarks){
                                Log.d("CameraPreview", "landmark: $i")
                            }
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
        LogPreView()
        if(posedata != null){
            posedata?.forEach{
                Canvas(modifier = Modifier.fillMaxSize()){
                    drawCircle(
                        color = Color.Red,
                        radius = 10f,
                        center = Offset(it.position.x.dp.toPx(), it.position.y.dp.toPx())
                    )
                }
            }
        }
    }


}