package com.example.todonotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextClock
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.R
import com.example.todonotes.clickListener.ItemClickListener
import com.example.todonotes.model.Notes

class NotesAdapter(val list: List<Notes>, val itemClickListener: ItemClickListener): RecyclerView.Adapter<NotesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        val notes = list[position]
        var title = notes.title
        var description = notes.description
        holder.textViewTitle.text = title
        holder.textViewDescription.text = description
        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                itemClickListener.onClick(notes)
            }
        })

    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var textViewTitle:TextView = itemView.findViewById(R.id.textViewTitle)
        var textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
    }

}