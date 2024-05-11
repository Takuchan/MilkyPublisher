package com.takuchan.milkypublisher.analysis

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.MutableState
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.takuchan.milkypublisher.model.DetectStateEnum

class FaceCaptureImageAnalyzer(
    private val faceDetectedState: (DetectStateEnum) -> Unit,
    private val returnFaces: (List<Face>) -> Unit
    ): ImageAnalysis.Analyzer {

        private var isFaceDetectedStart = false
    companion object{

        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()


        val faceDetector = FaceDetection.getClient(highAccuracyOpts)

    }
    override fun analyze(imageProxy: ImageProxy) {
        //listenerでImageinfo型を受け取り、unitで返す。
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )
            if(!isFaceDetectedStart){
                faceDetectedState(DetectStateEnum.Detected)
                isFaceDetectedStart = true
            }

            faceDetector.process(image)
                .addOnSuccessListener { faces ->
                    Log.d("DetectFaces",faces.toString())
                    returnFaces(faces)
                }
                .addOnFailureListener{e->

                }
                .addOnCompleteListener{
                    imageProxy.close()
                }
        }else{
            imageProxy.close()
        }

    }

}