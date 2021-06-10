package com.example.notella.dao

import androidx.room.*
import com.example.notella.entities.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAll(): List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Notes)

    @Delete
    fun delete(note: Notes)
}