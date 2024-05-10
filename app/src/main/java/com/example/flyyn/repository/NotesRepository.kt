    package com.example.flyyn.repository

    import com.example.flyyn.dao.iNotesDAO
    import com.example.flyyn.model.Notes
    import kotlinx.coroutines.flow.Flow

    class NotesRepository(private val dao: iNotesDAO) {
        val notes: Flow<List<Notes>> = dao.getAllNotes()

        suspend fun insert(note: Notes) {
            dao.insert(note)
        }

        suspend fun update(note: Notes) {
            dao.update(note)
        }

        suspend fun delete(note: Notes) {
            dao.delete(note)
        }
        fun getNoteById(id: Int): Flow<Notes> {
            return dao.getNoteById(id)
        }

        fun getFavoriteNotes(): Flow<List<Notes>> {
            return dao.getNotesByFavoriteStatus(true)
        }

        suspend fun getNotesCount(): Int {
            return dao.getNotesCount()
        }
    }
