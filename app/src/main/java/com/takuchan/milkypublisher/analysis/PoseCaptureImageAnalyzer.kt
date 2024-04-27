package com.takuchan.milkypublisher.analysis

import android.media.FaceDetector
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.takuchan.milkypublisher.model.DetectStateEnum
import com.takuchan.milkypublisher.preference.TmpUDPData

class PoseCaptureImageAnalyzer(
    private val poseState: (DetectStateEnum) -> Unit,
    private val poselandmarkListner: (MutableList<PoseLandmark>) -> (Unit),

): ImageAnalysis.Analyzer {

    private var isPoseDetectedStart = false
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
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )
            if(!isPoseDetectedStart){
                poseState(DetectStateEnum.Detected)
                isPoseDetectedStart = true
            }

            val poseDetectorTask: Task<Pose> = poseDetector.process(image)

            poseDetectorTask
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val pose = task.result
                    val allPoseLandmarks = pose.allPoseLandmarks
                    poselandmarkListner(allPoseLandmarks)

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