package com.example.notella.database
import com.example.notella.entities.Notes
import com.example.notella.dao.NotesDao


import androidx.room.*
import android.content.Context

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {

    companion object{
        var notesDatabase: NotesDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NotesDatabase{

            if(notesDatabase == null){
                notesDatabase = Room.databaseBuilder(context, NotesDatabase::class.java, "notes.db").build()
            }
            return notesDatabase!!
        }
    }

    abstract fun noteDao():NotesDao
}