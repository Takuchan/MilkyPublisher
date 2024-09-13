package com.takuchan.milkypublisher.data.model.publisherscreen

import com.google.mlkit.vision.face.FaceLandmark
import com.google.mlkit.vision.pose.PoseLandmark

data class PublisherDetectionState(
    var isPublisher: Boolean = false,
    var isDetectPose: Boolean = false,
    var isDetectFace: Boolean = false,
    var isDetectFaceSmiling: Boolean = false
)

data class PublisherState(
    val publisherDetectionState: PublisherDetectionState = PublisherDetectionState(),
    val poseLandmarks: List<PoseLandmark> = emptyList(),
    val faceLandmarks: List<List<FaceLandmark>> = emptyList()
)