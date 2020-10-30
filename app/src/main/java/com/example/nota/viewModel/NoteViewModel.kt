package com.example.nota.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.nota.db.NoteDB
import com.example.nota.db.NoteRepository
import com.example.nota.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application){
    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>
    init {
        val notesDao = NoteDB.getDatabase(application, viewModelScope).noteDao()
        repository = NoteRepository(notesDao)
        allNotes = repository.allNotes
    }
    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }
    fun deleteAll()= viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }
    fun updateNote(texto: String, titulo: String) = viewModelScope.launch{
        repository.updateNote(texto, titulo)
    }


}