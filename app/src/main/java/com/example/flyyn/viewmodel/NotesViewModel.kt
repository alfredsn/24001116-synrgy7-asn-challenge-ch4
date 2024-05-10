package com.example.flyyn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.flyyn.model.Notes
import com.example.flyyn.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    val notes = repository.notes.asLiveData(Dispatchers.IO)

    suspend fun insert(note: Notes) {
        repository.insert(note)
    }

    suspend fun update(note: Notes) {
        repository.update(note)
    }

    suspend fun getNotesCount(): Int {
        return repository.getNotesCount()
    }

    suspend fun favorite(note: Notes) {
        val updatedNote = note.copy(isFavorite = !note.isFavorite)
        repository.update(updatedNote)
    }

    suspend fun delete(note: Notes) {
        repository.delete(note)
    }

    fun getFavoriteNotes(): Flow<List<Notes>> {
        return repository.getFavoriteNotes()
    }

}