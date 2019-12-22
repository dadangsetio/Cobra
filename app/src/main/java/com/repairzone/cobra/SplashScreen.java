package com.repairzone.cobra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends AppCompatActivity {
    private int time=4000;
    Boolean session = false;
    SharedPreferences sharedPreferences;
    String username;

    public final static String TAG_USERNAME = "username";
    public static final String session_status = "session_status";
    public static final String my_shared_preferences = "my_shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(session_status, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(session){
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.putExtra(TAG_USERNAME, username);
                    finish();
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    intent.putExtra(session_status, session);
                    intent.putExtra(TAG_USERNAME, username);
                    finish();
                    startActivity(intent);
                }

            }
        },time);
    }
}
