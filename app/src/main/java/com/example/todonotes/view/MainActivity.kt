package com.example.todonotes.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotes.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        intent.getStringExtra("full_name")
//         intent.getFloatExtra("float_data");
        Log.d("myNotesActivity", intent.getStringExtra("full_name")!!)
    }
}
