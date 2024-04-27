package com.takuchan.milkypublisher.components

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.takuchan.milkypublisher.analysis.FaceCaptureImageAnalyzer
import com.takuchan.milkypublisher.analysis.PoseCaptureImageAnalyzer
import com.takuchan.milkypublisher.background.getCameraProvider
import com.takuchan.milkypublisher.compose.utils.LogPreView
import com.takuchan.milkypublisher.model.DetectTypeEnum
import com.takuchan.milkypublisher.model.LogData
import com.takuchan.milkypublisher.model.LogScreenData
import com.takuchan.milkypublisher.model.LogScreenEnum
import com.takuchan.milkypublisher.model.PoseLandmarkSingleDataClass
import com.takuchan.milkypublisher.preference.TmpUDPData
import com.takuchan.milkypublisher.viewmodel.LogViewModel
import com.takuchan.milkypublisher.viewmodel.PoseDetectPointViewModel
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.ExecutorService


@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(
    cameraExecutorService: ExecutorService,
    poseDetectPointViewModel: PoseDetectPointViewModel = viewModel(),
    logViewModel: LogViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val posedata by poseDetectPointViewModel.poseDetectPointList.observeAsState()

    var facePosition = remember {
        mutableStateOf(FacePosition(0f, 0f, 0f, 0f))
    }

    //CameraPreview関数の縦横の幅の大きさを取得
    var size by remember { mutableStateOf(Size.Zero) }

    //Base pose detector with streaming frames
    Box(
        modifier = Modifier
            .size(width = 480.dp, height = 640.dp)
            .fillMaxSize()
            .border(width = 2.dp, color = Color.Red)
    ) {

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



                val poseAnalyzer = ImageAnalysis.Builder()
                    .build()
                    .also{
                        it.setAnalyzer(
                            cameraExecutorService,
                            PoseCaptureImageAnalyzer({ detectedState ->
                                logViewModel.addLogList(
                                    LogData(
                                        detectType = DetectTypeEnum.PoseDetection,
                                        detectState = detectedState,
                                        detectTime = Date(),
                                        detectData = ""
                                    )
                                )
                                logViewModel.addLogScreenList(
                                    LogScreenData(
                                        Date(),
                                        LogScreenEnum.Pose.name,
                                        "検知開始しました"
                                    )
                                )
                            }, { poseLandmarks ->
                                val poseLandmarkSingleDateList: MutableList<PoseLandmarkSingleDataClass> =
                                    mutableListOf()
                                poseDetectPointViewModel.addPoseDetectPointList(poseLandmarks)
                                for (i in poseLandmarks) {
                                    poseLandmarkSingleDateList.add(
                                        PoseLandmarkSingleDataClass(
                                            i.landmarkType,
                                            i.position.x,
                                            i.position.y
                                        )
                                    )
                                }
                                TmpUDPData.putLandmarkListData(poseLandmarkSingleDateList)
                            })
                        )
                    }
                val faceAnalyzer = ImageAnalysis.Builder()
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutorService, FaceCaptureImageAnalyzer({ it ->
                            Log.d("facedetectoranalyzer","facedetectoranalyzer")
                            logViewModel.addLogList(
                                LogData(
                                    detectType = DetectTypeEnum.PoseDetection,
                                    detectState = it,
                                    detectTime = Date(),
                                    detectData = ""
                                )
                            )
                            logViewModel.addLogScreenList(
                                LogScreenData(
                                    Date(),
                                    LogScreenEnum.Face.name,
                                    "検出開始しました"
                                )
                            )
                        }, { faces ->
                            Log.d("facedetectoranalyzer","facedetectoranalyzer1")

                            faces.forEach { face ->
                                val bounds = face.boundingBox
                                facePosition.value = FacePosition(
                                    top = bounds.top.toFloat(),
                                    bottom = bounds.bottom.toFloat(),
                                    left = bounds.left.toFloat(),
                                    right = bounds.right.toFloat()
                                )
                                Log.d("faceDetection",face.smilingProbability.toString())
                                face.smilingProbability?.let { it1 -> TmpUDPData.putSmiling(it1) }
                            }

                        }))
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
                            poseAnalyzer,faceAnalyzer
                        )


                    } catch (ex: Exception) {
                        Log.e("CameraPreview", "Use case binding failed", ex)
                    }
                }
                previewView
            }
        )
        if (posedata != null) {
            posedata?.forEach {
                Canvas(modifier = Modifier
                    .size(480.dp, 640.dp)
                    .fillMaxSize()) {
                    drawCircle(
                        color = Color.Red,
                        radius = 10f,
                        center = Offset(
                            it.position.x * (size.width / 480),
                            it.position.y * (size.height / 640)
                        )
                    )
                }
            }
        }
        if (facePosition.value.top != 0f) {
            Canvas(modifier = Modifier
                .size(480.dp, 640.dp)
                .fillMaxSize()) {
                drawRoundRect(
                    color = Color.DarkGray,
                    topLeft = Offset(
                        facePosition.value.left * (size.width / 480),
                        facePosition.value.top * (size.height / 640)
                    ),
                    size = Size(
                        (facePosition.value.right - facePosition.value.left) * (size.width / 480),
                        (facePosition.value.bottom - facePosition.value.top) * (size.height / 640)
                    ),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
        LogPreView()

    }


}

data class FacePosition(
    var top: Float,
    var bottom: Float,
    var left: Float,
    var right: Float
)