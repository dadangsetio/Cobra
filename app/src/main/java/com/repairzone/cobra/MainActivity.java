package com.repairzone.cobra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.repairzone.cobra.Fragment.NewItemFragment;
import com.repairzone.cobra.Fragment.StockInFragment;
import com.repairzone.cobra.Fragment.StockList;
import com.repairzone.cobra.Fragment.StockOutFragment;


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
       // name = findViewById(R.id.name);
        //name.setText(username);
        Button lg = findViewById(R.id.logout);
    }

    public void addItem(View view){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout_additem, new NewItemFragment())
                .addToBackStack("add_item")
                .commit();
    }
    public void StockIn(View view){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout_stockin, new StockInFragment())
                .addToBackStack("stock_in")
                .commit();
    }
    public void StockOut(View view){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout_stockout, new StockOutFragment())
                .addToBackStack("stock_out")
                .commit();
    }
    public void StockList(View view){
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.frame_layout_list, new StockList())
//                .addToBackStack("stock_list")
//                .commit();
        Intent intent = new Intent(MainActivity.this, StockList.class);
        startActivity(intent);
    }
    public void LogOut(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SplashScreen.session_status, false);
        editor.putString(TAG_USERNAME, null);
        editor.apply();

        Intent intent = new Intent(MainActivity.this, Login.class);
        finish();
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
