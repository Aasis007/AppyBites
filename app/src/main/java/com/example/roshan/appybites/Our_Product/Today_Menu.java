package com.example.roshan.appybites.Our_Product;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.*;

import com.example.roshan.appybites.Http_Handler.HttpHandler;
import com.example.roshan.appybites.Model_Class.Catering_item;
import com.example.roshan.appybites.R;
import com.example.roshan.appybites.RecyclerView_Adapter.Today_Menu_Adapter;
import com.example.roshan.appybites.Today_Menu.day_five;
import com.example.roshan.appybites.Today_Menu.day_four;
import com.example.roshan.appybites.Today_Menu.day_seven;
import com.example.roshan.appybites.Today_Menu.day_six;
import com.example.roshan.appybites.Today_Menu.day_three;
import com.example.roshan.appybites.Today_Menu.day_two;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class Today_Menu extends Fragment implements View.OnClickListener{

    private View view;
    private RecyclerView recyclerView;
    private Today_Menu_Adapter mAdapter;
    private static String url = "http://appybite.com/index.php?route=api/food/products&category_id=59&limit=0&page=10&token=F46AxXU0XAhBZWbN7gIbEB81ZZHfJW2Q";

    private TextView txt_today,txt_sec_Day,txt_third_Day,txt_forth_day,txt_fifth_day,txt_sixth_day,txt_seventh_day;
    private TextView sec_date,thrd_Date,forth_date,fifth_Date,sixth_Date,sev_date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.today_menu, container, false);
        getActivity().invalidateOptionsMenu();
        txt_today=(TextView)view.findViewById(R.id.txt_today);
        sec_date=(TextView)view.findViewById(R.id.txt_sec_date);
        thrd_Date=(TextView)view.findViewById(R.id.txt_third_date);
        forth_date=(TextView)view.findViewById(R.id.txt_forth_date);
        fifth_Date=(TextView)view.findViewById(R.id.txt_fifth_date);
        sixth_Date=(TextView)view.findViewById(R.id.txt_sixth_date);
        sev_date=(TextView)view.findViewById(R.id.txt_seven_date);
        txt_sec_Day=(TextView)view.findViewById(R.id.txt_sec_day);
        txt_third_Day=(TextView)view.findViewById(R.id.txt_third_day);
        txt_forth_day=(TextView)view.findViewById(R.id.txt_forth_day);
        txt_fifth_day=(TextView)view.findViewById(R.id.txt_fifth_day);
        txt_sixth_day=(TextView)view.findViewById(R.id.txt_sixth_Day);
        txt_seventh_day=(TextView)view.findViewById(R.id.txt_seven_day);
        setHasOptionsMenu(true);
        txt_today.setOnClickListener(this);
        txt_sec_Day.setOnClickListener(this);
        txt_third_Day.setOnClickListener(this);
        txt_forth_day.setOnClickListener(this);
        txt_fifth_day.setOnClickListener(this);
        txt_sixth_day.setOnClickListener(this);
        txt_seventh_day.setOnClickListener(this);
        new JsonTask().execute();
        //today
        long date1 = System.currentTimeMillis();
        SimpleDateFormat sdf1 = new SimpleDateFormat(" EEEE - ");
        String dateString1 = sdf1.format(date1);
        txt_today.setText(dateString1 + "Today's Menu");

        //second day

        long date2 = System.currentTimeMillis() + (1000 * 60 * 60 * 24);
        SimpleDateFormat sdf2 = new SimpleDateFormat(" EE");
        String dateString2 = sdf2.format(date2);
        txt_sec_Day.setText(dateString2);

        long date_sec = System.currentTimeMillis() + (1000 * 60 * 60 * 24);
        SimpleDateFormat sdf_sec = new SimpleDateFormat("dd");
        String dateString_sec = sdf_sec.format(date_sec);
        sec_date.setText(dateString_sec);


        //third day

        long date3 = System.currentTimeMillis() + (1000 * 60 * 60 * 24*2);
        SimpleDateFormat sdf3 = new SimpleDateFormat(" EE");
        String dateString3 = sdf3.format(date3);
        txt_third_Day.setText(dateString3);

        long date_thrd = System.currentTimeMillis() + (1000 * 60 * 60 * 24*2);
        SimpleDateFormat sdf_thrd = new SimpleDateFormat("dd");
        String dateString_thrd = sdf_thrd.format(date_thrd);
        thrd_Date.setText(dateString_thrd);


        //forthday

        long date4 = System.currentTimeMillis() + (1000 * 60 * 60 * 24*3);
        SimpleDateFormat sdf4 = new SimpleDateFormat(" EE");
        String dateString4 = sdf4.format(date4);
        txt_forth_day.setText(dateString4);

        long date_forth = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 3);
        SimpleDateFormat sdf_forth = new SimpleDateFormat("dd");
        String dateString_forth = sdf_forth.format(date_forth);
        forth_date.setText(dateString_forth);

        //fifth day

        long date5 = System.currentTimeMillis() + (1000 * 60 * 60 * 24*4);
        SimpleDateFormat sdf5 = new SimpleDateFormat(" EE");
        String dateString5 = sdf5.format(date5);
        txt_fifth_day.setText(dateString5);

        long date_fifth = System.currentTimeMillis() + (1000 * 60 * 60 * 24*4);
        SimpleDateFormat sdf_fifth = new SimpleDateFormat("dd");
        String dateString_fifth = sdf_fifth.format(date_fifth);
        fifth_Date.setText(dateString_fifth);

        //sixth day

        long date6 = System.currentTimeMillis() + (1000 * 60 * 60 * 24*5);
        SimpleDateFormat sdf6 = new SimpleDateFormat(" EE");
        String dateString6 = sdf6.format(date6);
        txt_sixth_day.setText(dateString6);

        long date_six = System.currentTimeMillis() + (1000 * 60 * 60 * 24*5);
        SimpleDateFormat sdf_six = new SimpleDateFormat("dd");
        String dateString_six = sdf_six.format(date_six);
        sixth_Date.setText(dateString_six);

        //seventh day

        long date7 = System.currentTimeMillis() + (1000 * 60 * 60 * 24*6);
        SimpleDateFormat sdf7 = new SimpleDateFormat(" EE");
        String dateString7 = sdf7.format(date7);
        txt_seventh_day.setText(dateString7);

        long date_sev = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 6);
        SimpleDateFormat sdf_sev = new SimpleDateFormat("dd");
        String dateString_sev = sdf_sev.format(date_sev);
        sev_date.setText(dateString_sev);

//        FragmentManager fragmentManager = getFragmentManager();
//        {
//
//            fragmentManager.beginTransaction()
//                    .replace(R.id.content_frame
//                            , new day_one())
//                    .commit();}
        return view;
    }

    @Override
    public void onClick(View v) {
       if (v==txt_sec_Day) {
           //seconday

           startActivity(new Intent(getActivity(),day_two.class));

       }else if (v==txt_third_Day){
            //thirdday
           startActivity(new Intent(getActivity(),day_three.class));
        }else if (v==txt_forth_day){
            //forthday
           startActivity(new Intent(getActivity(),day_four.class));
        }else if (v==txt_fifth_day){
            //fifthday
           startActivity(new Intent(getActivity(),day_five.class));
        }else if (v==txt_sixth_day){
            //sixthday
           startActivity(new Intent(getActivity(),day_six.class));
        }else if (v==txt_seventh_day){
            //seventh
           startActivity(new Intent(getActivity(),day_seven.class));

        }else {
           //firstday
       }

    }

    private class JsonTask extends AsyncTask<String,Void,String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            pdLoading.dismiss();
            ArrayList<Catering_item> catering_items=new ArrayList<>();
            if (jsonStr !=null){
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray array=jsonObject.getJSONArray("products");

                    int size = array.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject j = array.getJSONObject(i);
                        Catering_item catering_item = new Catering_item();
                        catering_item.setProduct_id(j.optString("product_id"));
                        catering_item.setP_name(j.optString("name"));
                        catering_item.setP_desc(j.optString("description"));
                        catering_item.setP_ingredients(j.optString("ingredients"));
                        catering_item.setP_price(j.optString("pirce"));
                        catering_item.setChef_image(j.optString("chef_image"));
                        catering_item.setP_image(j.optString("image"));
                        String day=   catering_item.setP_day(j.optString("day"));
                        long date2 = System.currentTimeMillis() + (1000 * 60 * 60 * 24*2);
                        SimpleDateFormat sdf2 = new SimpleDateFormat("EE");
                        String dateString2 = sdf2.format(date2);
                       // String day2=dateString2.toLowerCase();
                       // System.out.println("DayTow : "+day2);
                        txt_sec_Day.setText(dateString2);
                        if (day.equals(dateString2.toLowerCase())){
                            catering_items.add(catering_item);
                        }

                    }
                    recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
                    mAdapter = new Today_Menu_Adapter(getActivity(), catering_items);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
