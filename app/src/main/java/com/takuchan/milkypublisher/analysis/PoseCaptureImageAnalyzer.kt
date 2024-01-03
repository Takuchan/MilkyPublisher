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
import com.takuchan.milkypublisher.preference.UDPController
import com.takuchan.milkypublisher.viewmodel.PoseDetectPointViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PoseCaptureImageAnalyzer(
    private val listener: (ImageProxy) -> Unit,
    private val poseListner: (MutableList<PoseLandmark>) -> Unit,
): ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy) {
        //listenerでImageinfo型を受け取り、unitで返す。
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )
            val options = PoseDetectorOptions.Builder()
                .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
                .build()
            val poseDetector = PoseDetection.getClient(options)
            val poseDetectorTask: Task<Pose> = poseDetector.process(image)

            poseDetectorTask
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val pose = task.result
                    Log.d("PoseDetectInfo","姿勢検出成功")
                    val allPoseLandmarks = pose.allPoseLandmarks

                    listener(imageProxy)
                    poseListner(allPoseLandmarks)
                    GlobalScope.launch {
                        // ここでWifiのUDPを処理させる
                        UDPController().send(pose)
                    }
                    // Task completed successfully
                    // ...
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