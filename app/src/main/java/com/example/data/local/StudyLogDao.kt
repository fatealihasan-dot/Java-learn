package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.StudyLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyLogDao {
    @Query("SELECT * FROM study_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<StudyLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: StudyLogEntity): Long

    @Delete
    suspend fun delete(log: StudyLogEntity)

    @Query("SELECT SUM(durationMinutes) FROM study_logs")
    fun getTotalMinutesFlow(): Flow<Int?>
}
