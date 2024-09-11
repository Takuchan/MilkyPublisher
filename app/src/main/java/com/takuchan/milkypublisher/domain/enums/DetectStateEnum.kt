package com.takuchan.milkypublisher.data.enums

enum class DetectStateEnum(val state: String) {
    Start("開始"),
    Detected("検出中"),
    Failed("失敗"),
    Stop("停止"),
}
