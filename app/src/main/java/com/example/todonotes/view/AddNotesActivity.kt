package com.example.todonotes.view

import android.accounts.AuthenticatorDescription
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
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
    val REQUEST_CODE_CAMERA = 1
    var picturePath = ""
    var MY_PERMISSION_CODE = 124
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
                if(checkAndRequestPermission()){
                    setupDialog()
                }

            }
        })
    }

    private fun checkAndRequestPermission(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val listPermissionNeeded = ArrayList<String>()

        if(cameraPermission != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(android.Manifest.permission.CAMERA)
        }
        if(storagePermission != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toTypedArray<String>(), MY_PERMISSION_CODE)
            return false
        }
        return true
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

    // handle the result here when we get data from gallery startActivity
    // we will use glide made by bumptech, helps us to use or display image in Image view

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                REQUEST_CODE_GALLERY -> {
                    val selectedImage = data?.data
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    // c is cursor, its used to do query in Global level in Android app
                    val c = contentResolver.query(selectedImage!!, filePath, null, null, null)
                    c!!.moveToFirst()
                    val columnIndex = c.getColumnIndex(filePath[0])
                    picturePath = c.getString(columnIndex)
                    c.close()
                    Log.d(TAG, picturePath)
                    Log.d(TAG, selectedImage.toString())
                    Log.d(TAG, filePath.toString())
                    Glide.with(this).load(picturePath).into(imageViewNotes)
                }
                REQUEST_CODE_CAMERA -> {

                }
            }
        }
    }
}