package com.takuchan.milkypublisher.di

import android.content.Context
import androidx.room.Room
import com.takuchan.milkypublisher.Dao.Welcome.RobotProgramDao
import com.takuchan.milkypublisher.database.RobotProgramDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDiaryDatabase(@ApplicationContext context: Context): RobotProgramDatabase {
        return Room.databaseBuilder(
            context,
            RobotProgramDatabase::class.java,
            "diary_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDiaryDao(database: RobotProgramDatabase): RobotProgramDao {
        return database.robotProgramDao()
    }
}