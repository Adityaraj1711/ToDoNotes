package com.example.todonotes.clickListener

import com.example.todonotes.model.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
}