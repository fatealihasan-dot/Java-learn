package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.LessonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons ORDER BY sortOrder ASC")
    fun getAllLessons(): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE id = :id LIMIT 1")
    fun getLessonById(id: String): Flow<LessonEntity?>

    @Query("SELECT * FROM lessons WHERE isCompleted = 1")
    fun getCompletedLessons(): Flow<List<LessonEntity>>

    @Query("SELECT COUNT(*) FROM lessons")
    suspend fun getLessonCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(lessons: List<LessonEntity>)

    @Query("UPDATE lessons SET isCompleted = :completed, completionTimestamp = :timestamp WHERE id = :id")
    suspend fun updateCompletion(id: String, completed: Boolean, timestamp: Long?)

    @Update
    suspend fun updateLesson(lesson: LessonEntity)
}
