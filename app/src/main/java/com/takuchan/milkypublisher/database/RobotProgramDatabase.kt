package com.takuchan.milkypublisher.database

import androidx.room.Database
import com.takuchan.milkypublisher.Dao.Welcome.RobotProgramDao
import com.takuchan.milkypublisher.Entity.Welcome.RobotProgramEntry
import androidx.room.RoomDatabase

@Database(entities = [RobotProgramEntry::class], version = 1, exportSchema = false)
abstract class RobotProgramDatabase: RoomDatabase() {
    abstract fun robotProgramDao(): RobotProgramDao
}