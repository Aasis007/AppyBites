package com.example.roshan.appybites.RecyclerView_Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.roshan.appybites.DatePickerFragment;
import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.example.roshan.appybites.Db_Handler.Fav_sql;
import com.example.roshan.appybites.Item_description;
import com.example.roshan.appybites.Model_Class.Catering_item;
import com.example.roshan.appybites.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by roshan on 5/20/17.
 */

public class Chef_detail_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private View view;
    private LayoutInflater inflater;
    ArrayList<Catering_item> catering_items = new ArrayList<>();
    private DbHandler mydb;
    String searchStr;
    public Chef_detail_adapter(Context context, ArrayList<Catering_item> catering_items) {
        this.context = context;
        if (context != null) {
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            // inflater= LayoutInflater.from(context);
        }
        this.catering_items = catering_items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.chef_detail, parent, false);
        MyHolder holder = new MyHolder(view);
        mydb = new DbHandler(context);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
// Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder = (MyHolder) holder;
        final Catering_item current = catering_items.get(position);
        myHolder.tvName.setText(current.getP_name());
        myHolder.tvprice.setText(current.getP_price());
        System.out.println("PRICE :"+current.getP_price());
        myHolder.tvMenu.setText(current.getMenu_cate());
        myHolder.tvprice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        //  myHolder.tvdiscount.setText("$ " + current.getDiscount_price());

        String[] quantity = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, quantity);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        myHolder.spinner1.setAdapter(adapter);

        // load image into imageview using glide
        Glide.with(context).load(current.getP_image())
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_camera)
                .into(myHolder.imageicon);
        myHolder.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = String.valueOf(myHolder.spinner1.getSelectedItemPosition());
                if (myHolder.spinner1.getSelectedItemPosition() > 0) {

                    float price = Float.parseFloat(myHolder.tvprice.getText().toString().replaceAll("[$]", ""));
                    float subtotal = (price) * (Integer.parseInt(selected));
                    myHolder.tv_subtotal.setText(String.format("%.02f", subtotal));
//                    myHolder.btn_Add_to_cart.setTag(subtotal);
//                    // Log.d("insert data: " + myDb.insertData(myHolder.tvName.getText().toString(),myHolder.tv_subtotal.getText().toString(),myHolder.spinner1.getSelectedItemPosition(),"null"));
                    mydb.insertData(current.getP_name(), String.valueOf(subtotal), Integer.parseInt(selected), "roshan");
//                    Toast.makeText(context, "" + current.getP_name() + " Added to cart", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public int getItemCount() {
        return catering_items.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private ImageView imageicon;
        private TextView tvName, tvprice, tv_subtotal,tv_dscount,tvday,tvMenu;
        private Spinner spinner1;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            imageicon = (ImageView) itemView.findViewById(R.id.item_image);
            tvName = (TextView) itemView.findViewById(R.id.txt_item_name);
            tvprice = (TextView) itemView.findViewById(R.id.txt_price);
            spinner1 = (Spinner) itemView.findViewById(R.id.spinner);
            tv_subtotal = (TextView) itemView.findViewById(R.id.txt_quantity);
            tvday=(TextView)itemView.findViewById(R.id.txt_day);
            tvMenu=(TextView)itemView.findViewById(R.id.txt_menu_cate);

//            tv_dscount.setPaintFlags(tv_dscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }}

