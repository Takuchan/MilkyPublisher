package com.takuchan.milkypublisher.graphic

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class PoseDetectorProcessor(
    private val context: Context,
    options: PoseDetectorOptionsBase,
    private val showInFrameLikelihood: Boolean,
    private val visualizeZ: Boolean,
    private val rescaleZForVisualization: Boolean,
    private val runClassification: Boolean,
    private val isStreamMode: Boolean
) : VisionProcessorBase<PoseDetectorProcessor.PoseWithClassification>(context) {
    private val detector: PoseDetector
    private val classificationExecutor: Executor

    private var poseClassifierProcessor: PoseClassifierProcessor? = null

    class PoseWithClassification(val pose: Pose, val classificationResult: List<String>)
    init {
        detector = PoseDetection.getClient(options)
        classificationExecutor = ScopedExecutor(Executors.newSingleThreadExecutor())
    }

    override fun stop() {
        super.stop()
        detector.close()
    }

    override fun detectInImage(image: InputImage): Task<PoseWithClassification> {
        return detector
            .process(image)
            .continueWith(
                classificationExecutor,
                {task ->
                    val pose = task.getResult()
                    val classficationResult: List<String> = ArrayList()
                    if (poseClassifierProcessor == null){
                        poseClassifierProcessor = PoseClassifierProcessor(context, isStreamMode)
                    }
                    classficationResult = poseClassifierProcessor!!.getPoseResult(pose)

                }
                PoseWithClassification(pose, classficationResult)
            )
    }
    override fun onSuccess(
        poseWithClassification: PoseWithClassification,
        graphicOverlay: GraphicOverlay
    ) {
        graphicOverlay.add(
            PoseGraphic(
                graphicOverlay,
                poseWithClassification.pose,
                showInFrameLikelihood,
                visualizeZ,
                rescaleZForVisualization,
                poseWithClassification.classificationResult
            )
        )
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Pose detection failed!", e)
    }

    override fun isMlImageEnabled(context: Context?): Boolean {
        // Use MlImage in Pose Detection by default, change it to OFF to switch to InputImage.
        return true
    }

    companion object {
        private val TAG = "PoseDetectorProcessor"
    }
}