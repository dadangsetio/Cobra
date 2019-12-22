package com.repairzone.cobra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.repairzone.cobra.API.DatabaseAPI;
import com.repairzone.cobra.Object.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.repairzone.cobra.SplashScreen.session_status;


public class Login extends AppCompatActivity {
    ProgressDialog progressDialog;
    @BindView(R.id.log_username) EditText edUsername;
    @BindView(R.id.log_password) EditText edPassword;
    ConnectivityManager connectivityManager;
    String TAG_SESSION = "session_status";
    String TAG_USERNAME = "username", username;
    Boolean session;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(SplashScreen.my_shared_preferences, Context.MODE_PRIVATE);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        session = getIntent().getBooleanExtra(TAG_SESSION, false);
        if(session){
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }
    }
    @OnClick(R.id.bt_login) void login(){
        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();

        if(username.trim().length() >0 && password.trim().length() > 0){
            if(CheckConnectifity()){
                Login(username, password);
            }else{
                Toast.makeText(this, "Check your Connection!!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
    }
    public void Register(View view){
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, new RegisterFragment()).commit();
    }

    public void Login(final String user, String pass){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI api_db = retrofit.create(DatabaseAPI.class);
        Call<Value> call = api_db.login(user, pass);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                if(value.equals("1")){
                    // menyimpan login ke session
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(session_status, true);
                    editor.putString(TAG_USERNAME, user);
                    editor.commit();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra(TAG_USERNAME, username);
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Check your connection!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean CheckConnectifity(){
        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
