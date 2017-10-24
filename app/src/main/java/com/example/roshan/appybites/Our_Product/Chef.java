package com.example.roshan.appybites.Our_Product;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roshan.appybites.Http_Handler.HttpHandler;
import com.example.roshan.appybites.Model_Class.Chef_class;
import com.example.roshan.appybites.R;
import com.example.roshan.appybites.RecyclerView_Adapter.Chef_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chef extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private Chef_Adapter cAdapter;

    private static String url = "http://appybite.com/index.php?route=api/food/chefs&token=F46AxXU0XAhBZWbN7gIbEB81ZZHfJW2Q";

    public Chef() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.recycler_view, container, false);
        getActivity().invalidateOptionsMenu();
        new JsonTask().execute();
        return view;
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
            System.out.println("url "+ url);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            pdLoading.dismiss();
            List<Chef_class> chef=new ArrayList<>();
            if (jsonStr !=null){
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray array=jsonObject.getJSONArray("chefs");

                    int size = array.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject j = array.getJSONObject(i);
                        Chef_class chef_list1 = new Chef_class();
                        chef_list1.setChef_id(j.optString("chef_id"));
                        chef_list1.setChef_name(j.optString("name"));
                        chef_list1.setChef_image(j.optString("image"));
                        chef_list1.setChef_description(j.optString("description"));
                        chef.add(chef_list1);
                    }
                    recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
                    cAdapter = new Chef_Adapter(getActivity(),chef);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(cAdapter);
                    cAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
