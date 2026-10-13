package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.SeedData
import com.example.data.model.CodingChallenge
import com.example.data.model.LessonEntity
import com.example.data.model.QuizQuestion
import com.example.data.model.SavedSnippetEntity
import com.example.data.model.StudyLogEntity
import com.example.data.model.UserStatsEntity
import com.example.data.repository.JavaTrackRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JavaTrackViewModel(
    private val repository: JavaTrackRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val selectedModuleFilter = MutableStateFlow<String?>("ALL")
    val selectedLesson = MutableStateFlow<LessonEntity?>(null)

    val allLessons: StateFlow<List<LessonEntity>> = repository.allLessons
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val userStats: StateFlow<UserStatsEntity> = repository.userStats
        .map { it ?: UserStatsEntity() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserStatsEntity()
        )

    val studyLogs: StateFlow<List<StudyLogEntity>> = repository.studyLogs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val savedSnippets: StateFlow<List<SavedSnippetEntity>> = repository.savedSnippets
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val filteredLessons: StateFlow<List<LessonEntity>> = combine(
        allLessons,
        searchQuery,
        selectedModuleFilter
    ) { lessons, query, filter ->
        lessons.filter { lesson ->
            val matchesFilter = when (filter) {
                null, "ALL" -> true
                "COMPLETED" -> lesson.isCompleted
                "INCOMPLETE" -> !lesson.isCompleted
                else -> lesson.moduleId == filter
            }
            val matchesQuery = query.isBlank() ||
                    lesson.title.contains(query, ignoreCase = true) ||
                    lesson.titleBn.contains(query, ignoreCase = true) ||
                    lesson.summaryBn.contains(query, ignoreCase = true) ||
                    lesson.moduleTitleBn.contains(query, ignoreCase = true)

            matchesFilter && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val quizzes: List<QuizQuestion> = SeedData.sampleQuizzes
    val challenges: List<CodingChallenge> = SeedData.sampleChallenges

    init {
        viewModelScope.launch {
            repository.initializeIfNeeded()
        }
    }

    fun selectLesson(lesson: LessonEntity?) {
        selectedLesson.value = lesson
    }

    fun toggleLessonCompletion(lesson: LessonEntity) {
        viewModelScope.launch {
            val newCompleted = !lesson.isCompleted
            repository.toggleLessonCompletion(lesson.id, newCompleted, lesson.xpReward)
            if (selectedLesson.value?.id == lesson.id) {
                selectedLesson.value = lesson.copy(isCompleted = newCompleted)
            }
        }
    }

    fun addStudyLog(topic: String, minutes: Int, notes: String) {
        viewModelScope.launch {
            repository.addStudyLog(topic, minutes, notes)
        }
    }

    fun deleteStudyLog(log: StudyLogEntity) {
        viewModelScope.launch {
            repository.deleteStudyLog(log)
        }
    }

    fun saveSnippet(title: String, code: String, notes: String) {
        viewModelScope.launch {
            repository.saveSnippet(title, code, notes)
        }
    }

    fun deleteSnippet(snippet: SavedSnippetEntity) {
        viewModelScope.launch {
            repository.deleteSnippet(snippet)
        }
    }

    fun recordCodeRun() {
        viewModelScope.launch {
            repository.recordCodeRun()
        }
    }

    fun recordQuizCompletion(xp: Int) {
        viewModelScope.launch {
            repository.recordQuizCompletion(xp)
        }
    }
}

class JavaTrackViewModelFactory(
    private val repository: JavaTrackRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JavaTrackViewModel::class.java)) {
            return JavaTrackViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
