package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.SavedSnippetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SnippetDao {
    @Query("SELECT * FROM saved_snippets ORDER BY timestamp DESC")
    fun getAllSnippets(): Flow<List<SavedSnippetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(snippet: SavedSnippetEntity): Long

    @Delete
    suspend fun delete(snippet: SavedSnippetEntity)
}
