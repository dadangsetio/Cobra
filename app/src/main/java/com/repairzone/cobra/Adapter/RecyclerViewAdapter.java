package com.repairzone.cobra.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.repairzone.cobra.Object.Stock;
import com.repairzone.cobra.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Stock> itemList;

    public RecyclerViewAdapter(List<Stock> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stock result = itemList.get(position);
        holder.tx_item.setText(result.getNama());
        holder.tx_jumlah.setText(Integer.toString(result.getJumlah()));
        holder.tx_satuan.setText(result.getSatuan());
    }

    @Override
    public int getItemCount() {
        return (itemList != null) ? itemList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tx_item;
        TextView tx_jumlah;
        TextView tx_satuan;

        public ViewHolder(View view){
            super(view);
            tx_item = view.findViewById(R.id.textItem);
            tx_jumlah = view.findViewById(R.id.textJumlah1);
            tx_satuan = view.findViewById(R.id.textSatuan);
        }
    }
}
