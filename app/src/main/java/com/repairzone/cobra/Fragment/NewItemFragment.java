package com.repairzone.cobra.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.repairzone.cobra.API.DatabaseAPI;
import com.repairzone.cobra.Configuration;
import com.repairzone.cobra.Object.Item;
import com.repairzone.cobra.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewItemFragment extends Fragment {

    View view;
    Button bt_acc;
    EditText ed_namaItem;
    Spinner sp_satuan;
    ProgressDialog progressDialog;
    String nama, satuan;

    public NewItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_item, container, false);
        bt_acc = view.findViewById(R.id.confrm_newitem);
        ed_namaItem = view.findViewById(R.id.nama_item);
        sp_satuan = view.findViewById(R.id.spinner_satuan);
        bt_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = ed_namaItem.getText().toString();
                satuan = sp_satuan.getSelectedItem().toString();
                addItem();
            }
        });
        return view;
    }

    public void addItem(){
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        DatabaseAPI api_db = retrofit.create(DatabaseAPI.class);
        Call<Item> call = api_db.addItem(nama, satuan);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                String value = response.body().getNama();
                String message = response.body().getSatuan();
                progressDialog.dismiss();
                if(value.equals("1")){
                    ed_namaItem.setText(" ");
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), "Check your connection!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
