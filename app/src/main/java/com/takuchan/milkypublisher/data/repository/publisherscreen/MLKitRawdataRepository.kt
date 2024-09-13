package com.takuchan.milkypublisher.data.repository.publisherscreen

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceLandmark
import com.google.mlkit.vision.pose.PoseLandmark

interface MLKitRawdataRepository {
    suspend fun detectPoseLandMarks(image: InputImage): List<PoseLandmark>
    suspend fun detectFaceLandMarks(image: InputImage): List<List<FaceLandmark>>
}