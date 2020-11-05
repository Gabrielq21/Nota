package com.example.nota

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nota.adapter.NoteAdapter
import com.example.nota.entities.Note
import com.example.nota.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var noteViewModel: NoteViewModel

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener{

    private val AddNoteRequestCode = 1
    private val UpdateActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NoteAdapter(this,this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, {notes ->
            notes?.let{adapter.setNotes(it)}
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNote::class.java)
            startActivityForResult(intent, AddNoteRequestCode)
        }

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback( 0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.deleteNote( adapter.getNoteAt(viewHolder.adapterPosition) )
            }

        }

        val itemTouchHelper = ItemTouchHelper( itemTouchHelperCallback )
        itemTouchHelper.attachToRecyclerView( recyclerview )

    }

    override fun onItemClicked(note: Note ) {
        val intent = Intent( this, UpdateNote::class.java)
        intent.putExtra(UpdateNote.EXTRA_ID, note.id)
        intent.putExtra(UpdateNote.EXTRA_REPLY, note.titulo)
        intent.putExtra(UpdateNote.EXTRA_Date, note.date)
        intent.putExtra(UpdateNote.EXTRA1_REPLY, note.texto)
        startActivityForResult(intent, UpdateActivityRequestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AddNoteRequestCode && resultCode == RESULT_OK) {
            val titulo = data?.getStringExtra(AddNote.EXTRA_REPLY).toString()
            val texto =  data?.getStringExtra(AddNote.EXTRA1_REPLY).toString()
            val date =   data?.getStringExtra(AddNote.EXTRA2_REPLY).toString()
            val note = Note(titulo = (titulo), texto = (texto), date = (date))
             noteViewModel.insert(note)


        }
        else if(requestCode == AddNoteRequestCode) {
            Toast.makeText(applicationContext,"Campos Incompletos",Toast.LENGTH_LONG).show()
        }
        if (requestCode == UpdateActivityRequestCode && resultCode == RESULT_OK) {
            val id = data?.getIntExtra( UpdateNote.EXTRA_ID, -1 )

            val titulo = data?.getStringExtra( UpdateNote.EXTRA_REPLY ).toString()
            val texto = data?.getStringExtra( UpdateNote.EXTRA1_REPLY ).toString()
            val date = data?.getStringExtra( UpdateNote.EXTRA_Date ).toString()
            val note = Note(id,titulo,texto,date)

            noteViewModel.updateNote(note)
        }
        else if(requestCode == UpdateActivityRequestCode) {
            Toast.makeText(applicationContext,"Campos Incompletos",Toast.LENGTH_LONG).show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater =menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.deleteall -> {
                noteViewModel.deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }





}