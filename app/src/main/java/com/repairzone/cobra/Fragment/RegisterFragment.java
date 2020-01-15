package com.repairzone.cobra.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.repairzone.cobra.API.DatabaseAPI;
import com.repairzone.cobra.Configuration;
import com.repairzone.cobra.Login;
import com.repairzone.cobra.Object.Value;
import com.repairzone.cobra.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterFragment extends Fragment {
    View view;
    EditText et_username, et_password, et_retypepswd;
    Button bt_register;
    TextView tv;
    String username, password, retypepasswd;
    ProgressDialog progress;
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        et_username = view.findViewById(R.id.reg_et_username);
        et_password = view.findViewById(R.id.reg_et_password);
        et_retypepswd = view.findViewById(R.id.reg_et_retypepassword);
        bt_register = view.findViewById(R.id.reg_bt_register);
        tv = view.findViewById(R.id.tv);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                retypepasswd = et_retypepswd.getText().toString().trim();
                Regist();
            }
        });
        return view;
    }
    public void Regist(){
        progress = new ProgressDialog(view.getContext());
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI api_db = retrofit.create(DatabaseAPI.class);
        Call<Value> call = api_db.register(username, password);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                startActivity(new Intent(view.getContext(), Login.class));
                if(value.equals("1")){
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(view.getContext(), "Check your connection!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
