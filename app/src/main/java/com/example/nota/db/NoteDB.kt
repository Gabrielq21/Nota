package com.example.nota.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nota.dao.NoteDao
import com.example.nota.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 2, exportSchema = false)
public abstract class NoteDB : RoomDatabase(){
    abstract fun noteDao(): NoteDao
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                     /*var noteDao = database.noteDao()
                     noteDao.deleteAll()
                     var name = Note(1,"Gabriel", "aluno","12")
                     noteDao.insert(name)
                     name= Note(2, "Edgar", "Professor","wq")
                     noteDao.insert(name) */
                }
            }
        }
    }
    companion object{
        @Volatile
        private var INSTANCE: NoteDB? = null
        fun getDatabase(context: Context, scope: CoroutineScope): NoteDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java,
                    "notes_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}