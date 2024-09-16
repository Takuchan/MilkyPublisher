package com.takuchan.milkypublisher.Dao.Welcome

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.takuchan.milkypublisher.Entity.Welcome.RobotProgramEntry
import kotlinx.coroutines.flow.Flow


@Dao
interface RobotProgramDao {
    @Query("SELECT * FROM robot_programs ORDER BY id DESC")
    fun getAllEntries(): Flow<List<RobotProgramEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(robotProgramEntity: RobotProgramEntry)

    @Delete
    suspend fun deleteEntry(robotProgramEntity: RobotProgramEntry)
    
}