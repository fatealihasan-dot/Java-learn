package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey
    val id: String,
    val moduleId: String,
    val moduleTitle: String,
    val moduleTitleBn: String,
    val title: String,
    val titleBn: String,
    val summary: String,
    val summaryBn: String,
    val difficulty: String, // Beginner, Intermediate, Advanced
    val estimatedMinutes: Int,
    val xpReward: Int,
    val isCompleted: Boolean = false,
    val completionTimestamp: Long? = null,
    val codeExample: String,
    val expectedOutput: String,
    val theoryContentBn: String,
    val keyPointsJson: String, // JSON or comma-separated key takeaways
    val sortOrder: Int
)

data class QuizQuestion(
    val id: String,
    val lessonId: String,
    val question: String,
    val questionBn: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanationBn: String,
    val codeSnippet: String? = null
)

data class CodingChallenge(
    val id: String,
    val title: String,
    val titleBn: String,
    val descriptionBn: String,
    val starterCode: String,
    val testInput: String,
    val expectedOutput: String,
    val hintBn: String,
    val difficulty: String,
    val xpReward: Int
)
