package com.example.todonotes

import android.app.Application
import com.example.todonotes.db.NotesDatabase

class NotesApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
    // initialize the dependency
    // access the database
    fun getNotesDb(): NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}
