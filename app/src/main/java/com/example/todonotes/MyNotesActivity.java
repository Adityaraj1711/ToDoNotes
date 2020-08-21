package com.example.todonotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todonotes.adapter.NotesAdapter;
import com.example.todonotes.clickListener.ItemClickListener;
import com.example.todonotes.model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyNotesActivity extends AppCompatActivity {
    String TAG = "MyNotesActivity";
    FloatingActionButton fabAddNotes;
    String fullName;
    String userName;
    RecyclerView recyclerViewNotes;
    SharedPreferences sharedPreferences;
    ArrayList<Notes> notesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        bindView();
        setupSharedPreferences();
        getIntentData();
        getSupportActionBar().setTitle(fullName);

        fabAddNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("myNotesActivity", "fab working");
                setupDialogBox(); // to popup after pressing +
            }
        });
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        fullName = intent.getStringExtra(AppConstant.FULL_NAME);
        userName = intent.getStringExtra(AppConstant.USER_NAME);
        if(TextUtils.isEmpty(fullName)){
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "");
        }
        if(TextUtils.isEmpty(userName)){
            userName = sharedPreferences.getString(PrefConstant.USER_NAME, "");
        }
    }

    private void bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }

    private void setupDialogBox() {
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout, null);
        // view group here is NULL
        // now access the editFields
        final EditText editTextTitle = view.findViewById(R.id.textInputEditTitle);
        final EditText editTextDescription = view.findViewById(R.id.textInputEditDescription);
        Button buttonSubmit = view.findViewById(R.id.buttonAddNotes);

        // now create popup which is called "Dialog" in android
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
//                .setCancelable(false) // this method is used if we want to close the dialog only on submitting
                .create();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                if(!TextUtils.isEmpty(title) && !(TextUtils.isEmpty(description))) {
                    Notes notes = new Notes();
                    notes.setTitle(title);
                    notes.setDescription(description);
                    notesList.add(notes);
                    Log.d(TAG, notesList.toString());
                } else {
                    Toast.makeText(MyNotesActivity.this, "Empty title or description", Toast.LENGTH_SHORT).show();
                }
                setupRecyclerView();

                dialog.hide();
            }
        });
        dialog.show();
    }

    private void setupRecyclerView() {
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Notes notes) {
                Log.d(TAG, "On click worked");
                Log.d(TAG, notes.getTitle().toUpperCase());
                Intent intent = new Intent(MyNotesActivity.this, DetailActivity.class);
                intent.putExtra(AppConstant.TITLE, notes.getTitle());
                intent.putExtra(AppConstant.DESCRIPTION, notes.getDescription());
                startActivity(intent);

            }
        };
        NotesAdapter notesAdapter = new NotesAdapter(notesList, itemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(notesAdapter);
    }

}