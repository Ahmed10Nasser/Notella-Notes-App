package com.example.notella.dao

import androidx.room.*
import com.example.notella.entities.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY id DESC")
    suspend fun getAll(): List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Notes)

    @Delete
    suspend fun delete(note: Notes)
}