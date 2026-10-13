package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey
    val id: Int = 1,
    val totalXp: Int = 0,
    val streakDays: Int = 1,
    val lastActiveEpochDay: Long = 0L,
    val totalMinutesStudied: Int = 0,
    val completedLessonsCount: Int = 0,
    val completedQuizzesCount: Int = 0,
    val codeRunsCount: Int = 0,
    val currentGoalMinutesPerDay: Int = 30
)

@Entity(tableName = "study_logs")
data class StudyLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val topicName: String,
    val durationMinutes: Int,
    val notes: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_snippets")
data class SavedSnippetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val code: String,
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
