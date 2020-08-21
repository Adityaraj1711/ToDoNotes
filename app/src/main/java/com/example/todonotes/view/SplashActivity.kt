package com.example.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotes.util.PrefConstant
import com.example.todonotes.R

class SplashActivity : AppCompatActivity(){
    private val TAG = "SplashActivity"
    //    var sharedPrefrences : SharedPreferences? = null  or
    // this represent the initialization will be later
    lateinit var sharedPrefrences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "splash activity initialized")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreferences()
        checkLoginStatus()
    }

    private fun setupSharedPreferences() {
        sharedPrefrences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPrefrences.getBoolean(PrefConstant.IS_LOGGED_IN, false)
        if(isLoggedIn){
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}