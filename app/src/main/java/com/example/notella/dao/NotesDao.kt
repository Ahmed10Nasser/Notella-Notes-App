package com.example.notella.dao

import androidx.room.*
import com.example.notella.entities.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAll(): List<Notes>

    @Query("SELECT * FROM notes WHERE id =:id")
    suspend fun getNote(id:Int) : Notes

    @Query("DELETE FROM notes WHERE id =:id")
    suspend fun deleteNote(id:Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Notes)

    @Update
    suspend fun updateNote(note:Notes)
}