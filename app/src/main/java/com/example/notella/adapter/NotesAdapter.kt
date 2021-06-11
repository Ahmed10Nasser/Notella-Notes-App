package com.example.notella.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notella.entities.Notes
import com.example.notella.R
import kotlinx.android.synthetic.main.notes_item.view.*


class NotesAdapter(val notesList : List<Notes>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return  NotesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.Title.text = notesList[position].title
        holder.itemView.NoteText.text = notesList[position].text
        holder.itemView.DateTime.text = notesList[position].dateTime
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}