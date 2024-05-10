package com.example.flyyn.dao

import androidx.room.*
import com.example.flyyn.model.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface iNotesDAO {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Notes)

    @Update
    suspend fun update(note: Notes)

    @Delete
    suspend fun delete(note: Notes)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Flow<Notes>

    @Query("SELECT * FROM notes WHERE isFavorite = :isFavorite")
    fun getNotesByFavoriteStatus(isFavorite: Boolean): Flow<List<Notes>>

    @Query("SELECT COUNT(*) FROM notes")
    suspend fun getNotesCount(): Int

}