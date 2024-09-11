package com.takuchan.milkypublisher.data.repository.publisherscreen

import com.takuchan.milkypublisher.ViewModel.publisherscreen.PublisherScreenViewModel
import com.takuchan.milkypublisher.data.model.publisherscreen.PublisherDetectionState
import javax.inject.Inject

class PublisherScreenDataSource @Inject constructor(
    private val publisherScreenViewModel: PublisherScreenViewModel
): PublisherScreenRepository {

    override fun setPublishState(state: Boolean) {
        publisherScreenViewModel.setPublishState(state)
    }

    override fun setPoseDetection(state: Boolean) {
        publisherScreenViewModel.setPoseDetection(state)
    }

    override fun setFaceDetection(state: Boolean) {
        publisherScreenViewModel.setFaceDetection(state)
    }

    override fun setFaceDetectionSmiling(state: Boolean) {
        publisherScreenViewModel.setFaceDetectionSmiling(state)

    }

    override fun getDetectionsList(): PublisherDetectionState {
        return publisherScreenViewModel.uiState.value.publisherDetectionState
    }
}