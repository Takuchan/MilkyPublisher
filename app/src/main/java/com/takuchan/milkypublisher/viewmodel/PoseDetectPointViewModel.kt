package com.takuchan.milkypublisher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark

class PoseDetectPointViewModel: ViewModel() {
    val _poseDetectPointList = MutableLiveData<MutableList<PoseLandmark>>()
    val poseDetectPointList: MutableLiveData<MutableList<PoseLandmark>> = _poseDetectPointList

    fun addPoseDetectPointList(data: MutableList<PoseLandmark>){
        _poseDetectPointList.value = data
    }
}