package com.takuchan.milkypublisher.Entity.Welcome

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "robot_programs")
data class RobotProgramEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long,
    val content:String
)