package com.takuchan.milkypublisher.data.repository.publisherscreen

import android.view.Display
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import timber.log.Timber
import javax.inject.Inject

class MLKitLandMarkDetector @Inject constructor() : MLKitRawdataRepository {
    companion object {
        private val options = PoseDetectorOptions.Builder()
            .setPreferredHardwareConfigs(PoseDetectorOptions.CPU_GPU)
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()
        val poseDetector = PoseDetection.getClient(options)

        val highFastOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        val faceDetector = FaceDetection.getClient(highFastOpts)
    }

    override suspend fun detectPoseLandMarks(image: InputImage): List<PoseLandmark> =
        withContext(Dispatchers.Default) {
            try {
                val result = Tasks.await(poseDetector.process(image))
                result.allPoseLandmarks
            } catch (e: Exception) {
                Timber.e(e, "Error detecting pose landmarks")
                emptyList()
            }
        }

    override suspend fun detectFaceLandMarks(image: InputImage): List<List<FaceLandmark>> =
        withContext(Dispatchers.Default) {
            try {
                val result = Tasks.await(faceDetector.process(image))
                result.map { face -> face.allLandmarks }
            } catch (e: Exception) {
                Timber.e(e, "Error detecting face landmarks")
                emptyList()
            }
        }
}