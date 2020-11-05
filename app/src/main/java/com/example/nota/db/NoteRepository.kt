package com.example.nota.db

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.nota.dao.NoteDao
import com.example.nota.entities.Note

class NoteRepository(private val noteDao: NoteDao){
    val allNotes: LiveData<List<Note>> = noteDao.getNotes()

    suspend fun deleteAll(){
        noteDao.deleteAll()
    }
    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }
    suspend fun deleteNote( note: Note ) {
        noteDao.deleteNote( note )
    }

}