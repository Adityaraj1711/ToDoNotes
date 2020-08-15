package com.example.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    String TAG = "DetailActivity";
    TextView textViewTitle, textViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bindViews();
        setupIntent();
    }

    private void setupIntent() {
        Intent intent = getIntent();
        Log.d(TAG, intent.getStringExtra(AppConstant.TITLE));
        String title = intent.getStringExtra(AppConstant.TITLE);
        String description = intent.getStringExtra(AppConstant.DESCRIPTION);
        textViewTitle.setText(title);
        textViewDescription.setText(description);
    }

    private void bindViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
    }
}