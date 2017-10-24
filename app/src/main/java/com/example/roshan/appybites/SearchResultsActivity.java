package com.example.roshan.appybites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.example.roshan.appybites.Db_Handler.Fav_sql;
import com.example.roshan.appybites.Db_Handler.Search_view;
import com.example.roshan.appybites.Http_Handler.HttpHandler;
import com.example.roshan.appybites.Model_Class.Catering_item;
import com.example.roshan.appybites.Nav_view_menu.Favourite;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultsActivity extends AppCompatActivity {
    private EditText txt_query;
    private ProgressDialog pDialog;
    private ListView listView;
    private GridView list;
    private Search_view mydb;
    private DbHandler myDb;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        list = (GridView) findViewById(R.id.fav_list);
        search = (SearchView) findViewById(R.id.searchView1);
        search.setQueryHint("Start typing to search...");
        mydb = new Search_view(getApplicationContext());
        myDb =new DbHandler(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.appybite);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //   search_text=txt_query.getText().toString();
        // Enabling Back navigation on Action Bar icon
        //  new GetContacts().execute();

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 3) {
                    list.setVisibility(View.VISIBLE);
                    final Cursor cursor = mydb.getData(newText);
                    String[] from = {mydb.col_2, mydb.col_3, mydb.col_4};
                    int[] to = {R.id.txt_item_name, R.id.txt_price, R.id.item_image};
                    final cursor_adapter cd = new cursor_adapter(SearchResultsActivity.this, R.layout.fav_item, cursor, from, to, 0);
                    list.setAdapter(cd);
                   // SearchResultsActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                } else {

                    list.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });

    }


    public class cursor_adapter extends SimpleCursorAdapter {
        LayoutInflater inflater;
        private int layout;


        public cursor_adapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        public class ViewHolder {
            private Spinner spinner;
            private TextView txt_item, txt_price, tv_quantity, tv_subtotal;
            private ImageView item_img;
            private Button btn_add_cart;

        }

        public View newView(Context _context, Cursor _cursor, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.fav_item, parent, false);
           ViewHolder holder = new ViewHolder();
            view.setTag(holder);
            holder.spinner = (Spinner) view.findViewById(R.id.spinner);
            holder.txt_item = (TextView) view.findViewById(R.id.txt_item_name);
            holder.txt_price = (TextView) view.findViewById(R.id.txt_price);
            holder.item_img = (ImageView) view.findViewById(R.id.item_image);
            holder.tv_subtotal = (TextView) view.findViewById(R.id.txt_id);
            holder.btn_add_cart = (Button) view.findViewById(R.id.btn_Add_to_Cart);
            holder.tv_quantity = (TextView) view.findViewById(R.id.quantity);
            return view;
        }

        @Override
        public void bindView(View view, Context context, final Cursor cursor) {
            super.bindView(view, context, cursor);
            final ViewHolder holder = (ViewHolder) view.getTag();
            final String item = cursor.getString(cursor.getColumnIndex(mydb.col_2));
            holder.txt_item.setText(item);
            final String price = cursor.getString(cursor.getColumnIndex(mydb.col_3));
            holder.txt_price.setText(price);
            final String image = cursor.getString(cursor.getColumnIndex(mydb.col_4));
            Picasso.with(context).load(image).placeholder(R.drawable.ic_menu_camera)
                    .into(holder.item_img);
            System.out.println("Image View:" + image);
            final float price1 = Float.parseFloat(holder.txt_price.getText().toString().replaceAll("[$]", ""));
            String[] quantity = {"25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, quantity);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            holder.spinner.setAdapter(adapter);

            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selected = parent.getItemAtPosition(position).toString();
                    holder.tv_quantity.setTag(selected);
                    float subtotal = (price1) * (Integer.parseInt(selected));
                    holder.tv_subtotal.setText(String.format(String.valueOf(subtotal)));
                    holder.btn_add_cart.setTag(subtotal);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                }

            });
            holder.btn_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = Integer.parseInt(holder.tv_quantity.getTag().toString());
                    float subtotal1 = Float.parseFloat(holder.btn_add_cart.getTag().toString());
                    // Log.d("insert data: " + myDb.insertData(myHolder.tvName.getText().toString(),myHolder.tv_subtotal.getText().toString(),myHolder.spinner1.getSelectedItemPosition(),"null"));
                    myDb.insertData(item, String.valueOf(subtotal1), count, "roshan");
                    Toast.makeText(getApplicationContext(), "" + item + " Added to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
            default:
                invalidateOptionsMenu();
                return super.onOptionsItemSelected(item);
        }
    }

}