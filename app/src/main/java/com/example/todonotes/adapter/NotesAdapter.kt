package com.example.todonotes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todonotes.R
import com.example.todonotes.clickListener.ItemClickListener
import com.example.todonotes.db.Notes

//import com.example.todonotes.model.Notes


class NotesAdapter(val list: List<Notes>, val itemClickListener: ItemClickListener): RecyclerView.Adapter<NotesAdapter.ViewHolder>(){
    val TAG = "NotesAdapter"
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

        holder.checkBoxMarkStatus.isChecked = notes.isTaskCompleted
        Glide.with(holder.itemView).load(notes.imagePath).into(holder.imageView)
        holder.checkBoxMarkStatus.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                Log.d(TAG, isChecked.toString())
                notes.isTaskCompleted = isChecked
                itemClickListener.onUpdate(notes)
            }
        })
    }

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var textViewTitle:TextView = itemView.findViewById(R.id.textViewTitle)
        var textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        var checkBoxMarkStatus: CheckBox = itemView.findViewById(R.id.checkboxMarkStatus)
        var imageView: ImageView = itemView.findViewById(R.id.imageViewNotes)
    }

}