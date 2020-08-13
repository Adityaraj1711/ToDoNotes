package com.example.todonotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyNotesActivity extends AppCompatActivity {
    FloatingActionButton fabAddNotes;
    String fullName;
    String userName;
    TextView textViewTitle, textViewDescription;
    SharedPreferences sharedPreferences;
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
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setEnabled(false);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewDescription.setEnabled(false);
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
        dialog.show();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                Log.d("NoteActivity", "title");
                textViewTitle.setText(title);
                textViewDescription.setText(description);
                dialog.hide();
            }
        });
    }
}