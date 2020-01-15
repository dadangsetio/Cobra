package com.repairzone.cobra.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.repairzone.cobra.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockOutFragment extends Fragment {

    View view;
    public StockOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stock_out, container, false);
        return view;
    }

}
