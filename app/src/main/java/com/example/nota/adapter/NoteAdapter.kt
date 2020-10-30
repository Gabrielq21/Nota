package com.example.nota.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nota.R
import com.example.nota.entities.Note


class NoteAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>() // Cached copy of words

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteItemView: TextView = itemView.findViewById(R.id.textView)
        val dateItemView: TextView = itemView.findViewById(R.id.date)
        val notesubItemView: TextView = itemView.findViewById(R.id.textview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]
        holder.noteItemView.text = current.titulo
        holder.dateItemView.text = current.date
        holder.notesubItemView.text = current.texto
    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}