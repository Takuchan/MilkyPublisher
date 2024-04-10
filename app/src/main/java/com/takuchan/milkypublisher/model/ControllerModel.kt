package com.takuchan.milkypublisher.model

import android.health.connect.datatypes.units.Velocity

data class ControllerModel(
    val velocity: Float,
    val upArrow: Boolean,
    val downArrow: Boolean,
    val leftArrow: Boolean,
    val rightArrow: Boolean,
)

