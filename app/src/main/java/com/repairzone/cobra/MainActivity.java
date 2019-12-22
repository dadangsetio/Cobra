package com.repairzone.cobra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String username;
    String TAG_USERNAME = "username";
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SplashScreen.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);
        name = findViewById(R.id.name);
        name.setText(username);
        Button lg = findViewById(R.id.logout);
    }
    public void LogOut(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SplashScreen.session_status, false);
        editor.putString(TAG_USERNAME, null);
        editor.commit();

        Intent intent = new Intent(MainActivity.this, Login.class);
        finish();
        startActivity(intent);

    }

}
