package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todonotes.NotesApp
import com.example.todonotes.util.AppConstant
import com.example.todonotes.util.PrefConstant
import com.example.todonotes.R
import com.example.todonotes.adapter.NotesAdapter
import com.example.todonotes.clickListener.ItemClickListener
import com.example.todonotes.db.Notes
import com.example.todonotes.workmanager.MyWorker
//import com.example.todonotes.model.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.TimeUnit

public class MyNotesActivity : AppCompatActivity(){
    val TAG = "MyNotesActivity"
    var fullName: String = ""
    var userName: String = ""
    val ADD_NOTES_CODE = 100
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var sharedPreferences: SharedPreferences
    var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindView()
        setupSharedPreferences()
        getIntentData()
        Log.d("MyNotesActivity", "onCreate")
        getDataFromDatabase()
        // before we were submitting to recycler view when we enter in dialog, but now we call it from onCreate
        setupRecyclerView()
        supportActionBar?.title = fullName
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
//                setupDialogBox()
                val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
                startActivityForResult(intent, ADD_NOTES_CODE)
            }
        })
        setupWorkManager()
    }

    private fun setupDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)
        val editTextTitle = view.findViewById<EditText>(R.id.textInputEditTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.textInputEditDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonAddNotes)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        buttonSubmit.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                if(title.isNotEmpty() && description.isNotEmpty()){
                    val notes = Notes(title = title, description = description)
                    notesList.add(notes)
                    addNotesToDb(notes)
                } else {
                    Toast.makeText(this@MyNotesActivity, "Title or description missing", Toast.LENGTH_SHORT).show()
                }
                dialog.hide()
            }
        })
        dialog.show()
    }

    private fun getDataFromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        Log.d(TAG, notesDao.getAll().size.toString())
        notesList.addAll(notesDao.getAll())
    }


    private fun addNotesToDb(notes: Notes) {
        // insert notes in DB
        val notesApp = applicationContext as NotesApp
        // notesDao is the abstract function created in database
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setupRecyclerView() {
        val itemClickListener = object : ItemClickListener{
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                Log.d(TAG, notes.isTaskCompleted.toString())
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }
        }
        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = notesAdapter

    }

    private fun getIntentData() {
        val intent = intent
        if(intent.hasExtra(AppConstant.FULL_NAME)){
            fullName = intent.getStringExtra(AppConstant.FULL_NAME)!!
        }
        if(intent.hasExtra(AppConstant.USER_NAME)){
            userName = intent.getStringExtra(AppConstant.USER_NAME)!!
        }
        if(fullName.isEmpty()){
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "").toString()
        }
        if (userName.isEmpty()) {
            userName = sharedPreferences.getString(PrefConstant.USER_NAME, "").toString()
        }

    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerView = findViewById(R.id.recyclerViewNotes)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_NOTES_CODE){
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)

            val notes = Notes(title = title!!, description = description!!, imagePath = imagePath!!, isTaskCompleted = false)
            addNotesToDb(notes)
            notesList.add(notes)
            // now show the data to recycler view
            recyclerView.adapter?.notifyItemChanged(notesList.size - 1)
        }
    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
                .build()
//                .setRequiresCharging(true)
//                .setRequiresBatteryNotLow(true)

        val request = PeriodicWorkRequest
                .Builder(MyWorker::class.java, 20, TimeUnit.SECONDS)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance().enqueue(request)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.blogs){
            Log.d(TAG, "click successful")
            Toast.makeText(this@MyNotesActivity, "New blog will be generated", Toast.LENGTH_LONG).show()
            val intent = Intent(this@MyNotesActivity, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}