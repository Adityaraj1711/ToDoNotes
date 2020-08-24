package com.example.todonotes.clickListener

import com.example.todonotes.db.Notes

//import com.example.todonotes.model.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)

    fun onUpdate(notes: Notes)
}