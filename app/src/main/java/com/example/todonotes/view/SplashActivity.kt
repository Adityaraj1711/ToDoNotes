package com.example.todonotes.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.todonotes.util.PrefConstant
import com.example.todonotes.R
import com.example.todonotes.onBoarding.OnBoardingActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

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
        getFCMToken()
        setupNotification()
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    val token = task.result?.token
                    // Log and toast
//                    val msg = getString(R.string.msg_fmt, token)
                    Log.d(TAG, token.toString())
                    Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                })
    }

    private fun setupSharedPreferences() {
        sharedPrefrences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPrefrences.getBoolean(PrefConstant.IS_LOGGED_IN, false)
        val isBoardingSuccess = sharedPrefrences.getBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY, false)
        if(isLoggedIn){
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        } else {
            if(isBoardingSuccess){
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                startActivity(intent)
            }
        }
        // to kill the instance of activity
        finish()
    }


    private fun setupNotification() {
        val channelId = "To Do"
        val body = "this is a local notification"
        val ringtone =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("To Do Notes App")
                .setContentText(body)
                .setSound(ringtone)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, "Local Channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

}