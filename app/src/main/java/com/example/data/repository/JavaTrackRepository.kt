package com.example.data.repository

import com.example.data.local.LessonDao
import com.example.data.local.SeedData
import com.example.data.local.SnippetDao
import com.example.data.local.StudyLogDao
import com.example.data.local.UserStatsDao
import com.example.data.model.LessonEntity
import com.example.data.model.SavedSnippetEntity
import com.example.data.model.StudyLogEntity
import com.example.data.model.UserStatsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate

class JavaTrackRepository(
    private val lessonDao: LessonDao,
    private val userStatsDao: UserStatsDao,
    private val studyLogDao: StudyLogDao,
    private val snippetDao: SnippetDao
) {
    val allLessons: Flow<List<LessonEntity>> = lessonDao.getAllLessons()
    val userStats: Flow<UserStatsEntity?> = userStatsDao.getUserStats()
    val studyLogs: Flow<List<StudyLogEntity>> = studyLogDao.getAllLogs()
    val savedSnippets: Flow<List<SavedSnippetEntity>> = snippetDao.getAllSnippets()

    suspend fun initializeIfNeeded() {
        val currentCount = lessonDao.getLessonCount()
        if (currentCount == 0) {
            lessonDao.insertAll(SeedData.initialLessons)
        }

        // Initialize User Stats and check streak
        val currentStats = userStatsDao.getUserStats().firstOrNull()
        val todayEpochDay = LocalDate.now().toEpochDay()

        if (currentStats == null) {
            val initial = UserStatsEntity(
                id = 1,
                totalXp = 100,
                streakDays = 1,
                lastActiveEpochDay = todayEpochDay,
                totalMinutesStudied = 45,
                completedLessonsCount = 1,
                completedQuizzesCount = 1,
                codeRunsCount = 3
            )
            userStatsDao.insertOrUpdate(initial)
            // Mark first lesson completed as a starter achievement
            lessonDao.updateCompletion("java_01_intro", true, System.currentTimeMillis())
        } else {
            // Check streak logic
            val diff = todayEpochDay - currentStats.lastActiveEpochDay
            val updatedStreak = when {
                diff == 0L -> currentStats.streakDays // Already logged today
                diff == 1L -> currentStats.streakDays + 1 // Streak continues!
                diff > 1L -> 1 // Streak reset
                else -> currentStats.streakDays
            }
            if (diff > 0L) {
                userStatsDao.update(
                    currentStats.copy(
                        streakDays = updatedStreak,
                        lastActiveEpochDay = todayEpochDay
                    )
                )
            }
        }
    }

    suspend fun toggleLessonCompletion(lessonId: String, completed: Boolean, xpReward: Int) {
        val timestamp = if (completed) System.currentTimeMillis() else null
        lessonDao.updateCompletion(lessonId, completed, timestamp)

        val stats = userStatsDao.getUserStats().firstOrNull() ?: UserStatsEntity()
        val xpDelta = if (completed) xpReward else -xpReward
        val countDelta = if (completed) 1 else -1

        val updated = stats.copy(
            totalXp = (stats.totalXp + xpDelta).coerceAtLeast(0),
            completedLessonsCount = (stats.completedLessonsCount + countDelta).coerceAtLeast(0),
            lastActiveEpochDay = LocalDate.now().toEpochDay()
        )
        userStatsDao.insertOrUpdate(updated)
    }

    suspend fun addStudyLog(topic: String, minutes: Int, notes: String) {
        val log = StudyLogEntity(
            topicName = topic,
            durationMinutes = minutes,
            notes = notes
        )
        studyLogDao.insert(log)

        val stats = userStatsDao.getUserStats().firstOrNull() ?: UserStatsEntity()
        val gainedXp = minutes * 2 // 2 XP per minute studied
        val updated = stats.copy(
            totalMinutesStudied = stats.totalMinutesStudied + minutes,
            totalXp = stats.totalXp + gainedXp,
            lastActiveEpochDay = LocalDate.now().toEpochDay()
        )
        userStatsDao.insertOrUpdate(updated)
    }

    suspend fun deleteStudyLog(log: StudyLogEntity) {
        studyLogDao.delete(log)
    }

    suspend fun saveSnippet(title: String, code: String, notes: String) {
        val snippet = SavedSnippetEntity(
            title = title,
            code = code,
            notes = notes
        )
        snippetDao.insert(snippet)
    }

    suspend fun deleteSnippet(snippet: SavedSnippetEntity) {
        snippetDao.delete(snippet)
    }

    suspend fun recordCodeRun() {
        val stats = userStatsDao.getUserStats().firstOrNull() ?: UserStatsEntity()
        val updated = stats.copy(
            codeRunsCount = stats.codeRunsCount + 1,
            totalXp = stats.totalXp + 5,
            lastActiveEpochDay = LocalDate.now().toEpochDay()
        )
        userStatsDao.insertOrUpdate(updated)
    }

    suspend fun recordQuizCompletion(xp: Int) {
        val stats = userStatsDao.getUserStats().firstOrNull() ?: UserStatsEntity()
        val updated = stats.copy(
            completedQuizzesCount = stats.completedQuizzesCount + 1,
            totalXp = stats.totalXp + xp,
            lastActiveEpochDay = LocalDate.now().toEpochDay()
        )
        userStatsDao.insertOrUpdate(updated)
    }
}
