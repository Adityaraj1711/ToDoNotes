package com.example.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.todonotes.PrefConstant.IS_LOGGED_IN;

public class LoginActivity extends AppCompatActivity {
    String TAG = "LoginActivity";
    EditText editTextUserName;
    EditText editTextFullName;
    Button buttonLogin;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUserName = findViewById(R.id.textInputEditUserName);
        editTextFullName = findViewById(R.id.textInputEditFullName);
        buttonLogin = findViewById(R.id.buttonLogin);
        setupSharedPreference();

        View.OnClickListener clickAction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity", "onClickOnLoginButton");
                String fullName = editTextFullName.getText().toString();
                String userName = editTextUserName.getText().toString();
                if(!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName)){
                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                    intent.putExtra(AppConstant.FULL_NAME, fullName);
                    intent.putExtra(AppConstant.USER_NAME, userName);
                    startActivity(intent);
                    // here we are successfully logged in
                    // so here we will use shared pref to store the state of logged in user
                    saveLoginStatus();
                    saveFullNameUserName(fullName, userName);
                } else {
                    Toast.makeText(LoginActivity.this, "Full name and Username required", Toast.LENGTH_SHORT).show();
                }
            }
        };
        buttonLogin.setOnClickListener(clickAction);
    }

    private void saveFullNameUserName(String fullName, String userName) {
        editor = sharedPreferences.edit();
        editor.putString(PrefConstant.FULL_NAME, fullName);
        editor.putString(PrefConstant.USER_NAME, userName);
        editor.apply();
    }

    private void saveLoginStatus() {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefConstant.IS_LOGGED_IN, true);
        editor.apply();
    }

    private void setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

//    @Override
//    protected void onStart() {
//        Log.d(TAG, "onStart");
//        super.onStart();
//    }
//
//    @Override
//    protected void onPause() {
//        Log.d(TAG, "onPause");
//        super.onPause();
//    }
//    @Override
//    protected void onRestart() {
//        Log.d(TAG, "onRestart");
//        super.onRestart();
//    }
//    @Override
//    protected void onResume() {
//        Log.d(TAG, "onResume");
//        super.onResume();
//    }
//    @Override
//    protected void onStop() {
//        Log.d(TAG, "onStop");
//        super.onStop();
//    }
//    @Override
//    protected void onDestroy() {
//        Log.d(TAG, "onDestroy");
//        super.onDestroy();
//    }

}