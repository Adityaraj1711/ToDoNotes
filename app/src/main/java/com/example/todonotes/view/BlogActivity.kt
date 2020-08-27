package com.example.todonotes.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.todonotes.R
import com.example.todonotes.adapter.BlogAdapter
import com.example.todonotes.model.JsonResponse
import kotlinx.android.synthetic.main.activity_blog.*


class BlogActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    val TAG = "BlogActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("https://www.mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(Priority.HIGH)
                .build()
                // we want the response in form of class object
                .getAsObject(JsonResponse::class.java, object : ParsedRequestListener<JsonResponse>{
                    override fun onResponse(response: JsonResponse?) {
                        Log.d(TAG, "recycler view setup")
                        setupRecyclerView(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d(TAG, "error")
                        Log.d(TAG, anError?.localizedMessage.toString())
                    }

                })
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recyclerViewBlogs)
    }

    private fun setupRecyclerView(response: JsonResponse?) {
        val blogAdapter = BlogAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlogs.layoutManager = linearLayoutManager
        recyclerViewBlogs.adapter = blogAdapter
    }


}