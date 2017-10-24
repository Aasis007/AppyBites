package com.example.roshan.appybites.Nav_view_menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.roshan.appybites.MainActivity;
import com.example.roshan.appybites.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
       public Button edtaccbtn;
       public Button edt
    View view;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        edtaccbtn=(Button)view.findViewById(R.id.button3);
                edtaccbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ;


                    }
                });
        return view;

    }
    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Profile");

    }
}
