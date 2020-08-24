package com.example.todonotes.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

// data access objects - DAO
// it helps us to work with data
// interface because it will help us communicate from view to database
@Dao
interface NotesDao{

    @Query("SELECT * from notesData")
    fun getAll(): List<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)
    @Update
    fun updateNotes(notes: Notes)
    @Delete
    fun delete(notes: Notes)
}

