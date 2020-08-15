package com.example.todonotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todonotes.MyNotesActivity;
import com.example.todonotes.R;
import com.example.todonotes.clickListener.ItemClickListener;
import com.example.todonotes.model.Notes;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    List<Notes> listNotes;
    ItemClickListener itemClickListener;

    public NotesAdapter(List<Notes> list, ItemClickListener itemClickListener){
        this.listNotes = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        final Notes notes = listNotes.get(position);
        String title = notes.getTitle();
        String description = notes.getDescription();
        holder.textViewTitle.setText(title);
        holder.textViewDescription.setText(description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClick(notes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle, textViewDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
