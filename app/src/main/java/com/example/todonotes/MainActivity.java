package com.example.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        intent.getStringExtra("full_name");
        // intent.getFloatExtra("float_data");
        Log.d("myNotesActivity", intent.getStringExtra("full_name"));

    }
}