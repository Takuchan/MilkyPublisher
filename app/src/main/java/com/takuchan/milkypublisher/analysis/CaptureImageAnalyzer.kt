package com.takuchan.milkypublisher.analysis

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageInfo
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer
class CaptureImageAnalyzer(
    private val listener: (ImageInfo) -> Unit): ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        //listenerでImageinfo型を受け取り、unitで返す。
        listener(image.imageInfo)
        image.close()
    }

}