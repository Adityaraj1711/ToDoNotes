package com.example.todonotes.view

import android.accounts.AuthenticatorDescription
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotes.R
import kotlinx.android.synthetic.main.activity_add_notes.*
import org.w3c.dom.Text

class AddNotesActivity : AppCompatActivity() {
    val TAG = "AddNotesActivity"
    lateinit var editTextTitle : EditText
    lateinit var editTextDescription : EditText
    lateinit var imageViewNotes : ImageView
    lateinit var buttonSubmit : Button
    val REQUEST_CODE_GALLERY = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        Log.d(TAG, "onCreate")
        bindViews()
        clickListener()
    }

    private fun clickListener() {
        imageViewNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                Log.d(TAG, "click Listener")
                setupDialog()
            }
        })
    }

    private fun setupDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_selector, null)
        val textViewCamera: TextView = view.findViewById(R.id.textViewCamera)
        val textViewGallery: TextView = view.findViewById(R.id.textViewGallery)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create()
        textViewCamera.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                //
            }
        })

        textViewGallery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                // two types of intent -> implicit and explicit
                val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_CODE_GALLERY)
            }

        })
        dialog.show()
    }

    private fun bindViews() {
        editTextTitle = findViewById(R.id.textInputEditTitle)
        editTextDescription = findViewById(R.id.textInputEditDescription)
        imageViewNotes = findViewById(R.id.imageViewNotes)
        buttonSubmit = findViewById(R.id.buttonAddNotes)
    }
}