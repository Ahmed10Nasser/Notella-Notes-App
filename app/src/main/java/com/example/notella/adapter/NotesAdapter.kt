package com.example.notella.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notella.entities.Notes
import com.example.notella.R
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.notes_item.*
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

        if(notesList[position].color != null){
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(notesList[position].color))
            if (notesList[position].color !="#606570" && notesList[position].color != "#ff202734" ){
                holder.itemView.Title.setTextColor(Color.parseColor("#10141C"))
                holder.itemView.NoteText.setTextColor(Color.parseColor("#171C26"))
                holder.itemView.DateTime.setTextColor(Color.parseColor("#171C26"))
            }
        }else{
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(R.color.ColorDefaultNote.toString()))
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}