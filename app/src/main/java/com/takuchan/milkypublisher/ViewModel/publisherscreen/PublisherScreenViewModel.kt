package com.takuchan.milkypublisher.ViewModel.publisherscreen

import androidx.lifecycle.ViewModel
import com.takuchan.milkypublisher.data.model.publisherscreen.PublisherDetectionState
import com.takuchan.milkypublisher.data.model.publisherscreen.PublisherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class PublisherScreenViewModel @Inject constructor(): ViewModel(){
    private val _uiState = MutableStateFlow<PublisherState>(PublisherState())
    val uiState: StateFlow<PublisherState> = _uiState

    fun setPublishState(state: Boolean){
        _uiState.value.publisherDetectionState.isPublisher = state
    }
    fun setPoseDetection(state: Boolean){
        _uiState.value.publisherDetectionState.isDetectPose = state
    }
    fun setFaceDetection(state: Boolean){
        _uiState.value.publisherDetectionState.isDetectFace = state
    }
    fun setFaceDetectionSmiling(state: Boolean){
        _uiState.value.publisherDetectionState.isDetectFaceSmiling = state
    }
}