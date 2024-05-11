package com.takuchan.milkypublisher.model

import com.google.mlkit.vision.pose.PoseLandmark
import kotlinx.serialization.Serializable

@Serializable
class JSONPushData (
    val pushedData: String,
    // AI系
    val poseLandmark: List<PoseLandmark>,
    //制御系
    val robotController: ControllerModel
)


