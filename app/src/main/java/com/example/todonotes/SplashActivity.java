package com.example.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    private static String TAG = "SplashActivity";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "splash activity initialized");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupSharedPreferences();
        checkLoginStatus();
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

    private void checkLoginStatus() {
        // if user is logged in -> myNotesActivity
        // else loginActivity
        // shared preferences
        boolean isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN,false);
        if(isLoggedIn){
            // notes activity
            Intent intent = new Intent(SplashActivity.this, MyNotesActivity.class);
            Log.d(TAG, "going to My Notes Screen");
            startActivity(intent);
        } else {
            // Login activity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            Log.d(TAG, "going to Login Screen");
            startActivity(intent);
        }
    }
}