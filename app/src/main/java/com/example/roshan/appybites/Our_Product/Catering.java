package com.example.roshan.appybites.Our_Product;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
;import com.example.roshan.appybites.DatePickerFragment;
import com.example.roshan.appybites.Db_Handler.Search_view;
import com.example.roshan.appybites.Http_Handler.HttpHandler;
import com.example.roshan.appybites.MainActivity;
import com.example.roshan.appybites.Model_Class.Catering_item;
import com.example.roshan.appybites.Model_Class.Constants;
import com.example.roshan.appybites.Nav_view_menu.Cart;
import com.example.roshan.appybites.R;
import com.example.roshan.appybites.RecyclerView_Adapter.Catering_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Catering extends Fragment {

    private View view;
    private Search_view myDb;
    private RecyclerView recyclerView;
    private Catering_Adapter mAdapter;
    String id= String.valueOf(63);
   // private static String url = "http://appybite.com/index.php?route=api/food/products&category_id="+id+"&limit=0&page=10&token=F46AxXU0XAhBZWbN7gIbEB81ZZHfJW2Q";

    public Catering() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.recycler_view, container, false);
        getActivity().invalidateOptionsMenu();
        myDb=new Search_view(getActivity());
        if (checkInternetOn())
        new JsonTask().execute();
        else {

            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
       // checkInternetOn();

        return view;
    }


    public boolean checkInternetOn() {

        ConnectivityManager mgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnectedOrConnecting()) {
                // Internet Available

            } else {
                Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
        }
        return netInfo!=null && netInfo.isConnected();
    }



    private class JsonTask extends AsyncTask<String,Void,String>{
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
           String jsonStr = sh.makeServiceCall("http://appybite.com/index.php?route=api/food/products&category_id="+id+"&limit=0&page=10&token=F46AxXU0XAhBZWbN7gIbEB81ZZHfJW2Q");
           return jsonStr;
       }

       @Override
       protected void onPostExecute(String jsonStr) {
           super.onPostExecute(jsonStr);
           pdLoading.dismiss();
           List<Catering_item> catering_items=new ArrayList<>();
           if (jsonStr !=null){
               try {
                   JSONObject jsonObject = new JSONObject(jsonStr);
                   JSONArray array=jsonObject.getJSONArray("products");

                   int size = array.length();
                   for (int i = 0; i < size; i++) {
                       JSONObject j = array.getJSONObject(i);
                       Catering_item catering_item = new Catering_item();
                       catering_item.setProduct_id(j.optString("product_id"));
                      String name= catering_item.setP_name(j.optString("name"));
                      catering_item.setP_desc(j.optString("description"));
                       catering_item.setP_ingredients(j.optString("ingredients"));
                      String price= catering_item.setP_price(j.optString("pirce"));
                       catering_item.setChef_image(j.optString("chef_image"));
                      String image=  catering_item.setP_image(j.optString("image"));
                      catering_items.add(catering_item);
                      System.out.println("Insert: "+"inserting in searcview");
                      myDb.insertData(name,price,image);
                   }
                   recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
                   mAdapter = new Catering_Adapter(getActivity(), catering_items);
                   RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
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
