package com.example.todonotes

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity: AppCompatActivity() {
    var TAG = "DetailActivity"
    lateinit var textViewTitle: TextView
    lateinit var textViewDescription:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setupIntent()
    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
    }

    private fun setupIntent() {
        val intent = intent  // getIntent()
        Log.d(TAG, intent.getStringExtra(AppConstant.TITLE)!!)
        val title = intent.getStringExtra(AppConstant.TITLE)
        val description = intent.getStringExtra(AppConstant.DESCRIPTION)
        // setText()
        textViewTitle.text = title
        textViewDescription.text = description
    }
}