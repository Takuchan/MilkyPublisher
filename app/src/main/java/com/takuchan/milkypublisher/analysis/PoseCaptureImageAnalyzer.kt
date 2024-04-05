package com.takuchan.milkypublisher.analysis

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.takuchan.milkypublisher.model.DetectStateEnum
import com.takuchan.milkypublisher.preference.UDPController
import com.takuchan.milkypublisher.viewmodel.DetectState
import com.takuchan.milkypublisher.viewmodel.PoseDetectPointViewModel
import com.takuchan.milkypublisher.viewmodel.UDPFlowViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PoseCaptureImageAnalyzer (
    private val poseState: (DetectStateEnum) -> Unit,
    private val poselandmarkListner: (MutableList<PoseLandmark>) -> (Unit),
): ImageAnalysis.Analyzer {
    companion object{
        private const val TAG = "PoseCaptureImageAnalyzer"
        val options = PoseDetectorOptions.Builder()
            .setPreferredHardwareConfigs(PoseDetectorOptions.CPU_GPU)
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()
        val poseDetector = PoseDetection.getClient(options)

    }
    override fun analyze(imageProxy: ImageProxy) {
        //listenerでImageinfo型を受け取り、unitで返す。
        val mediaImage = imageProxy.image
        Log.d("Resoltuinon", "analyze: ${mediaImage?.width} x ${mediaImage?.height}")

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            val poseDetectorTask: Task<Pose> = poseDetector.process(image)

            poseDetectorTask
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val pose = task.result
                    Log.d("PoseDetectInfo","姿勢検出成功")
                    val allPoseLandmarks = pose.allPoseLandmarks
                    poseState(DetectStateEnum.Detected)
                    poselandmarkListner(allPoseLandmarks)


                    // Task completed successfully
                    // ...
                    imageProxy.close()
                } else if(task.isCanceled){
                    val e = task.exception
                    imageProxy.close()
                    // Task failed with an exception
                    // ...
                }
            }
        }

    }

}