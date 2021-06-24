package com.example.notella.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notella.R
import com.example.notella.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.notes_item.view.*



class NotesAdapter(val notesList: List<Notes>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

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
            if (notesList[position].color !="#ff606570" && notesList[position].color != "#ff363636" ){
                holder.itemView.Title.setTextColor(Color.parseColor("#10141C"))
                holder.itemView.NoteText.setTextColor(Color.parseColor("#171C26"))
                holder.itemView.DateTime.setTextColor(Color.parseColor("#171C26"))
            }
        }else{
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(R.color.ColorDefaultNote.toString()))
        }

        if (notesList[position].imgUri != null){

            val uri = Uri.parse(notesList[position].imgUri)
            holder.itemView.imgRoundNote.setImageURI(uri)
            holder.itemView.imgRoundNote.visibility = View.VISIBLE


        }else{
            holder.itemView.imgRoundNote.visibility = View.GONE
        }

        if (notesList[position].link != ""){
            holder.itemView.Link.text = notesList[position].link
            holder.itemView.Link.visibility = View.VISIBLE
        }else{
            holder.itemView.Link.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}