package com.example.roshan.appybites.Nav_view_menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roshan.appybites.MainActivity;
import com.example.roshan.appybites.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Order_History extends Fragment {


    public Order_History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order__history, container, false);
    }
    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("History");

    }
}
