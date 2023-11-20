package com.takuchan.milkypublisher.graphic

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import androidx.arch.core.executor.TaskExecutor
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.TaskExecutors
import java.util.Timer
import java.util.concurrent.ExecutorService

class VisionBaseProcessor<T>(context: Context): VisionImageProcessor {
    companion object{
        const val MANUAL_TESTING_LOG = "LogTagForTest"
        private const val TAG = "VisionProcessorBase"
    }

    private var activityManager: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    private val fpsTimer = Timer()
    private val executor = ScopedExecutor(TaskExecutors.MAIN_THREAD)

    // すでに機能が停止しているかどうか
    private var isShutdown = false

    // Used to calculate latency, running in the same thread, no sync needed.
    private var numRuns = 0
    private var totalFrameMs = 0L
    private var maxFrameMs = 0L
    private var minFrameMs = Long.MAX_VALUE
    private var totalDetectorMs = 0L
    private var maxDetectorMs = 0L
    private var minDetectorMs = Long.MAX_VALUE

    // FPSを計測
    private var frameProcessedInOneSecondInterval = 0
    private var framesPerSecond = 0

    override fun processBitmap(bitmap: Bitmap?, graphicOverlay: GraphicOverlay?) {
        TODO("Not yet implemented")
    }

    override fun processByteBuffer(
        data: ByteArray?,
        frameMetadata: FrameMetadata?,
        graphicOverlay: GraphicOverlay?
    ) {
        TODO("Not yet implemented")
    }

    override fun processImageProxy(image: ImageProxy?, graphicOverlay: GraphicOverlay?) {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}