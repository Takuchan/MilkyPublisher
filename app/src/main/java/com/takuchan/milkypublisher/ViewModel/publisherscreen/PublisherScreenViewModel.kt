package com.takuchan.milkypublisher.ViewModel.publisherscreen

import android.media.Image
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceLandmark
import com.google.mlkit.vision.pose.PoseLandmark
import com.takuchan.milkypublisher.data.model.publisherscreen.PublisherState
import com.takuchan.milkypublisher.data.repository.publisherscreen.MLKitLandMarkDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PublisherScreenViewModel @Inject constructor(
    private val mlKitLandMarkDetector: MLKitLandMarkDetector
): ViewModel(){
    private val _uiState = MutableStateFlow(PublisherState())
    val uiState: StateFlow<PublisherState> = _uiState.asStateFlow()

    fun processImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            val image = imageProxy.image
            if (image != null) {
                val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
                try {
                    val currentState = _uiState.value.publisherDetectionState
                    var poseLandmarks = emptyList<PoseLandmark>()
                    var faceLandmarks = emptyList<List<FaceLandmark>>()

                    if (currentState.isDetectPose) {
                        poseLandmarks = mlKitLandMarkDetector.detectPoseLandMarks(inputImage)
                    }
                    if (currentState.isDetectFace) {
                        faceLandmarks = mlKitLandMarkDetector.detectFaceLandMarks(inputImage)
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            poseLandmarks = poseLandmarks,
                            faceLandmarks = faceLandmarks
                        )
                    }
                    Log.d("imageProcess", "Pose landmarks: ${poseLandmarks.size}, Face landmarks: ${faceLandmarks.size}")
                } catch (e: Exception) {
                    Log.e("imageProcess", "Error processing image", e)
                } finally {
                    imageProxy.close()
                }
            } else {
                imageProxy.close()
            }
        }
    }
    fun setPublishState(state: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                publisherDetectionState = currentState.publisherDetectionState.copy(
                    isPublisher = state
                )
            )
        }
    }

    fun setPoseDetection(state: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                publisherDetectionState = currentState.publisherDetectionState.copy(
                    isDetectPose = state
                )
            )
        }
    }

    fun setFaceDetection(state: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                publisherDetectionState = currentState.publisherDetectionState.copy(
                    isDetectFace = state
                )
            )
        }
    }

    fun setFaceDetectionSmiling(state: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                publisherDetectionState = currentState.publisherDetectionState.copy(
                    isDetectFaceSmiling = state
                )
            )
        }
    }

}