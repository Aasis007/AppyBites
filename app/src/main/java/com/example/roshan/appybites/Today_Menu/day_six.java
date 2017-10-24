package com.example.roshan.appybites.Today_Menu;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.roshan.appybites.BadgeDrawable;
import com.example.roshan.appybites.Carts;
import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.example.roshan.appybites.Http_Handler.HttpHandler;
import com.example.roshan.appybites.Model_Class.Catering_item;
import com.example.roshan.appybites.R;
import com.example.roshan.appybites.RecyclerView_Adapter.Today_Menu_Adapter;
import com.example.roshan.appybites.SearchResultsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class day_six extends AppCompatActivity {
    private DbHandler myDb;
    private RecyclerView recyclerView;
    private Today_Menu_Adapter mAdapter;
    private static String url = "http://appybite.com/index.php?route=api/food/products&category_id=59&limit=0&page=10&token=F46AxXU0XAhBZWbN7gIbEB81ZZHfJW2Q";
    private TextView ui_hot=null;
    private TextView txt_today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_two);
        txt_today=(TextView) findViewById(R.id.txt_today);
        new JsonTask().execute();
        long date2 = System.currentTimeMillis() + (1000 * 60 * 60 * 24*5);
        SimpleDateFormat sdf2 = new SimpleDateFormat(" dd MMM - EEEE");
        String dateString2 = sdf2.format(date2);
        txt_today.setText(dateString2);
        myDb=new DbHandler(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb=new DbHandler(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.appybite);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private class JsonTask extends AsyncTask<String,Void,String> {
        ProgressDialog pdLoading = new ProgressDialog(day_six.this);
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
                        catering_item.setP_price(j.optString("pirce"));
                        catering_item.setChef_image(j.optString("chef_image"));
                        catering_item.setP_image(j.optString("image"));
                        catering_items.add(catering_item);
                    }
                    recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                    mAdapter = new Today_Menu_Adapter(day_six.this, catering_items);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Cursor cursor = myDb.viewData();
        View item = menu.findItem(R.id.action_settings).getActionView();
        ui_hot = (TextView) item.findViewById(R.id.hotlist_hot);
        ui_hot.setText(String.valueOf(cursor.getCount()));
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Carts.class));
            }
        });

        cursor.close();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_search:
                this.finish();
                startActivity(new Intent(getApplicationContext(), SearchResultsActivity.class));
                return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }


}
