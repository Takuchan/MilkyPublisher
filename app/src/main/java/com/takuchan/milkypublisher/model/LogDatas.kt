package com.takuchan.milkypublisher.model

enum class DetectType(val type: String) {
    PoseDetection("姿勢検出"),
}

enum class DetectState(val state: String) {
    Start("開始"),
    Detected("検出中"),
    Failed("失敗"),
    Stop("停止"),
}