package com.example.notella.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.notella.R
import com.example.notella.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.notes_item.view.*



class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var listener:OnItemClickListener? = null
    var notesList= ArrayList<Notes>()
    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }
    // RecyclerView calls this method whenever it needs to create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return  NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notes_item, parent, false)
        )
    }
    //RecyclerView calls this method to associate a ViewHolder with data.
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        //view note text
        holder.itemView.Title.text = notesList[position].title
        holder.itemView.NoteText.text = notesList[position].text
        holder.itemView.DateTime.text = notesList[position].dateTime

        //view note color
        if(notesList[position].color != null){
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(notesList[position].color))
            //light text color for darker notes
            if (notesList[position].color !="#ff606570" && notesList[position].color != "#ff363636" ){
                holder.itemView.Title.setTextColor(Color.parseColor("#10141C"))
                holder.itemView.NoteText.setTextColor(Color.parseColor("#171C26"))
                holder.itemView.DateTime.setTextColor(Color.parseColor("#171C26"))
            }
        }else{
            holder.itemView.cardView.setCardBackgroundColor(Color.parseColor(R.color.ColorDefaultNote.toString()))
        }
        //view note image
        if (notesList[position].imgUri != null){

            val uri = Uri.parse(notesList[position].imgUri)
            holder.itemView.imgRoundNote.setImageURI(uri)
            holder.itemView.imgRoundNote.visibility = View.VISIBLE


        }else{
            holder.itemView.imgRoundNote.visibility = View.GONE
        }
        //view note link
        if (notesList[position].link != ""){
            holder.itemView.Link.text = notesList[position].link
            holder.itemView.Link.visibility = View.VISIBLE
        }else{
            holder.itemView.Link.visibility = View.GONE
        }

        //listener for a click on a note
        holder.itemView.cardView.setOnClickListener {
            listener!!.onClicked(notesList[position].id!!)
        }
    }

    // RecyclerView calls this method to get the size of the data set.
    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setData(arrNotesList: List<Notes>){
        notesList = arrNotesList as ArrayList<Notes>
    }

    interface OnItemClickListener{
        fun onClicked(noteId:Int)
    }

    fun setOnClickListener(clickListener:OnItemClickListener){
        listener = clickListener
    }




}