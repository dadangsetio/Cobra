package com.repairzone.cobra.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.repairzone.cobra.API.DatabaseAPI;
import com.repairzone.cobra.Adapter.ListViewAdapter;
import com.repairzone.cobra.Configuration;
import com.repairzone.cobra.MainActivity;
import com.repairzone.cobra.Object.Item;
import com.repairzone.cobra.Object.Stock;
import com.repairzone.cobra.Object.Value;
import com.repairzone.cobra.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockInFragment extends Fragment {

    View view;
    Button btn, btn_acc;
    LinearLayout lay1;
    ScrollView scrollView;
    int i =1;
    Context context;
    List<Item> itemList = new ArrayList<>();
    ListView lv_item;
    ArrayList<Stock> choose;
    ListViewAdapter listViewAdapter;
    ProgressDialog progress;

    public StockInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stock_in, container, false);
        context = view.getContext();
        btn = view.findViewById(R.id.btn);
        btn_acc = view.findViewById(R.id.btn_masuk);
        lay1 = view.findViewById(R.id.lay1);
        choose = new ArrayList<>();
        loadItemList();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addListItem();
                    i=1+i;
            }
        });
        btn_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = new ProgressDialog(view.getContext());
                progress.setCancelable(false);
                progress.setMessage("Loading ...");
                progress.show();
                for(int x = 0; x<choose.size(); x++){
                    String nama = choose.get(x).getNama();
                    int jumlah = choose.get(x).getJumlah();
                    String satuan = choose.get(x).getSatuan();
                    System.out.println(nama + " " +jumlah+ " "+satuan);
                    addStockIn(nama, jumlah, satuan);
                }
                progress.dismiss();
                choose.clear();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void addListItem(){
        scrollView = new ScrollView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(context);
        TextView tx_item = new TextView(context);
        Button btn = new Button(context);

        tx_item.setText("Pilih Item");
        tx_item.setId(i);
        tx_item.setGravity(Gravity.BOTTOM);
        tx_item.setWidth(400);
        layout.setLayoutParams(params);

        btn.setId(i);
        btn.setText("+");
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(20);

        layout.addView(tx_item);
        layout.addView(btn, params2);
        scrollView.addView(layout);
        lay1.addView(scrollView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View list = getLayoutInflater().inflate(R.layout.item_dialog, null);
                builder.setTitle("Pilih Barangnya");
                listViewAdapter = new ListViewAdapter(context, R.layout.list_item_view, itemList);
                lv_item = list.findViewById(R.id.item_listview);
                listViewAdapter.notifyDataSetChanged();
                lv_item.setAdapter(listViewAdapter);
                builder.setView(list);
                AlertDialog dialog = builder.create();
                lv_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        View jml_dialog = getLayoutInflater().inflate(R.layout.dialog_jml, null);
                        EditText ed_jml = jml_dialog.findViewById(R.id.ed_juml);
                        ed_jml.setSingleLine(true);
                        new AlertDialog.Builder(context)
                                .setView(jml_dialog)
                                .setTitle("Masukkan Jumlah")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int x) {
                                        String a = ed_jml.getText().toString();
                                        if(!a.isEmpty()){
                                            tx_item.setText(itemList.get(i).getNama()+" : "+a);
                                            choose.add(new Stock(
                                                    itemList.get(i).getNama(),
                                                    itemList.get(i).getSatuan(),
                                                    Integer.parseInt(ed_jml.getText().toString())
                                            ));
                                            Toast.makeText(getActivity(), itemList.get(i).getNama(), Toast.LENGTH_SHORT).show();
                                            ed_jml.setText("");
                                            dialog.cancel();
                                            dialogInterface.cancel();
                                        }else {
                                            Toast.makeText(context, "Tidak Boleh Kosong...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).show();
                    }
                });
                dialog.show();
            }
        });
    }

    private void loadItemList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI api = retrofit.create(DatabaseAPI.class);
        Call<Value> call = api.listItem();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        itemList = response.body().getItemList();
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }
            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(context, "Cek Koneksi....", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addStockIn(String n, int j, String s){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatabaseAPI api_db = retrofit.create(DatabaseAPI.class);
        Call<Stock> call = api_db.addStock(n, j, s);
        call.enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                Toast.makeText(context, "Sukses....", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(view.getContext(), "Check your connection!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
