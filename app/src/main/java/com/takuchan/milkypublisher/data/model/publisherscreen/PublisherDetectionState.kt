package com.takuchan.milkypublisher.data.model.publisherscreen

data class PublisherDetectionState(
    var isPublisher: Boolean = false,
    var isDetectPose: Boolean = false,
    var isDetectFace: Boolean = false,
    var isDetectFaceSmiling: Boolean = false
)

data class PublisherState(
    val publisherDetectionState: PublisherDetectionState = PublisherDetectionState()
)