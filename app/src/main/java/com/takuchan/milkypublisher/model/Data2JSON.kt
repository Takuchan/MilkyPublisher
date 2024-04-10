package com.takuchan.milkypublisher.model

import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable




@Serializable
data class PoseLandmarkDataclass(val landmarkId: Int, val landmarkX:Float,val landmarkY: Float)

@Serializable
data class DetectData(
    val pose: List<PoseLandmarkDataclass>
)


