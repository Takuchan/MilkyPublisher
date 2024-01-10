package com.takuchan.milkypublisher.model

import java.util.Date


data class LogData(
    val detectType: DetectTypeEnum,
    val detectState: DetectStateEnum,
    val detectTime: Date,
    val detectData: String,
)

enum class DetectTypeEnum(val type: String) {
    ApplicationDetection("MilkyPublisher"),
    PoseDetection("姿勢検出"),
}

enum class DetectStateEnum(val state: String) {
    Start("開始"),
    Detected("検出中"),
    Failed("失敗"),
    Stop("停止"),
}
