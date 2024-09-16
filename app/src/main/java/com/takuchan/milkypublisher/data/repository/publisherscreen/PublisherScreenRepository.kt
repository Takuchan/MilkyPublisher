package com.takuchan.milkypublisher.data.repository.publisherscreen

import com.takuchan.milkypublisher.data.model.publisherscreen.PublisherDetectionState

interface PublisherScreenRepository {
    fun setPublishState(state: Boolean)
    fun setPoseDetection(state: Boolean)
    fun setFaceDetection(state: Boolean)
    fun setFaceDetectionSmiling(state: Boolean)

    fun getDetectionsList(): PublisherDetectionState
}