package com.repairzone.cobra.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.repairzone.cobra.Object.Stock;
import com.repairzone.cobra.R;

import java.util.List;

public class ListViewStockAdapter extends ArrayAdapter<Stock> {
    List<Stock> listItem;
    Context context;
    int layout;

    public ListViewStockAdapter(Context context, int layout, List<Stock> list){
        super(context, layout, list);
        this.context=context;
        this.layout=layout;
        this.listItem=list;

    }

    public View getView(int position, View convertView, ViewGroup viewGroup){
        View view = convertView;
        ItemHolder holder;

        if(view==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layout, viewGroup, false);
            holder = new ItemHolder();
            holder.tx_nama = view.findViewById(R.id.list_nama);
            holder.tx_satuan = view.findViewById(R.id.list_satuan);

            view.setTag(holder);
        }else {
            holder = (ItemHolder) view.getTag();
        }
        Stock item = listItem.get(position);
        holder.tx_nama.setText(item.getNama());
        holder.tx_satuan.setText(item.getSatuan());

        return view;
    }

    public static class ItemHolder{
        TextView tx_nama;
        TextView tx_satuan;
    }
}


