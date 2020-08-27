package com.example.todonotes.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.todonotes.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService(){
    val TAG = "FirebaseMessageService"
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, message?.from.toString())
        Log.d(TAG, message?.notification?.body.toString())
        setupNotification(message?.notification?.body)
    }

    private fun setupNotification(body: String?) {
        val channelId = "To Do"
        val ringtone =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("To Do Notes App")
                .setContentText(body)
                .setSound(ringtone)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, "To-Do Notes", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }


    // If the token is changed upon re-installation/update we will get new token
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendRegistrationToServer(token)
        // this token and the token in getFCMToken is same.
    }
}
