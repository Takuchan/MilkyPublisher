package com.takuchan.milkypublisher.viewmodel

import com.google.mlkit.vision.pose.Pose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UDPFlowViewModel {
    val _poseInit:Pose? = null
    private val _pose = MutableStateFlow(_poseInit)
    val pose = _pose.asStateFlow()

    fun updatePose(newValue: Pose){
        _pose.value = newValue
    }
}