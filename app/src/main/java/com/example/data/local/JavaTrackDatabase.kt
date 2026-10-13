package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.LessonEntity
import com.example.data.model.SavedSnippetEntity
import com.example.data.model.StudyLogEntity
import com.example.data.model.UserStatsEntity

@Database(
    entities = [
        LessonEntity::class,
        UserStatsEntity::class,
        StudyLogEntity::class,
        SavedSnippetEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class JavaTrackDatabase : RoomDatabase() {
    abstract fun lessonDao(): LessonDao
    abstract fun userStatsDao(): UserStatsDao
    abstract fun studyLogDao(): StudyLogDao
    abstract fun snippetDao(): SnippetDao

    companion object {
        @Volatile
        private var INSTANCE: JavaTrackDatabase? = null

        fun getDatabase(context: Context): JavaTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JavaTrackDatabase::class.java,
                    "javatrack_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
