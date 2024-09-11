package com.takuchan.milkypublisher.Repository.RobotProgram

import com.takuchan.milkypublisher.Dao.Welcome.RobotProgramDao
import com.takuchan.milkypublisher.Entity.Welcome.RobotProgramEntry
import javax.inject.Inject

class RobotProgramRepository @Inject constructor(private val robotProgramDao: RobotProgramDao) {
    fun getAllEntries() = robotProgramDao.getAllEntries()

    suspend fun insertEntry(robotProgramEntity: RobotProgramEntry) = robotProgramDao.insertEntry(robotProgramEntity)
    suspend fun deleteEntry(robotProgramEntity: RobotProgramEntry) = robotProgramDao.deleteEntry(robotProgramEntity)
}