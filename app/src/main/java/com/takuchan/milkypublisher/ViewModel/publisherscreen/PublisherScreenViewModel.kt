package com.takuchan.milkypublisher.ViewModel.publisherscreen

import androidx.lifecycle.ViewModel
import com.takuchan.milkypublisher.data.model.publisherscreen.PublisherDetectionState
import com.takuchan.milkypublisher.data.model.publisherscreen.PublisherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PublisherScreenViewModel @Inject constructor(): ViewModel(){
    private val _uiState = MutableStateFlow(PublisherState())
    val uiState: StateFlow<PublisherState> = _uiState.asStateFlow()

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