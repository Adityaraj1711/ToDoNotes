package com.example.todonotes

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.example.todonotes.db.NotesDatabase

class NotesApp : Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }
    // initialize the dependency
    // access the database
    fun getNotesDb(): NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}
