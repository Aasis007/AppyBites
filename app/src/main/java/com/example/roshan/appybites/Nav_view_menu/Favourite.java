package com.example.roshan.appybites.Nav_view_menu;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.roshan.appybites.DatePickerFragment;
import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.example.roshan.appybites.Db_Handler.Fav_sql;
import com.example.roshan.appybites.MainActivity;
import com.example.roshan.appybites.R;
import com.example.roshan.appybites.SearchResultsActivity;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favourite extends Fragment {

    private View view;
    private GridView list;
    private Fav_sql mydb;
    private DbHandler myDb;
    public DatePickerFragment f_manager;
    private DatePickerFragment.OnItemClickListener listner;
    public int dateTime,dateyear,datemonth;
    Calendar dateAndTime = Calendar.getInstance();

    public Favourite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_favourite, container, false);
        list=(GridView) view.findViewById(R.id.fav_list);
        mydb=new Fav_sql(getActivity());
        myDb =new DbHandler(getActivity());

                    final Cursor cursor = mydb.getData();
                    String[] from = {mydb.col_2,mydb.col_3,mydb.col_4};
                    int[] to = {R.id.txt_item_name, R.id.txt_price,R.id.item_image};
                    final cursor_adapter cd = new cursor_adapter(view.getContext(), R.layout.fav_item, cursor, from, to, 0);
                    list.setAdapter(cd);


        return view;
    }

    public  class cursor_adapter extends SimpleCursorAdapter {
        LayoutInflater inflater;
        private int layout;


        public cursor_adapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        public class ViewHolder {
            private Spinner spinner;
            private TextView txt_item, txt_price, tv_quantity,tv_subtotal;
            private ImageView item_img;
            private Button btn_add_cart;

        }

        public View newView(Context _context, Cursor _cursor, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.fav_item, parent, false);
            cursor_adapter.ViewHolder holder = new cursor_adapter.ViewHolder();
            view.setTag(holder);
            holder.spinner = (Spinner) view.findViewById(R.id.spinner);
            holder.txt_item = (TextView) view.findViewById(R.id.txt_item_name);
            holder.txt_price = (TextView) view.findViewById(R.id.txt_price);
            holder.item_img = (ImageView) view.findViewById(R.id.item_image);
            holder.tv_subtotal=(TextView)view.findViewById(R.id.txt_id);
            holder.btn_add_cart=(Button)view.findViewById(R.id.btn_Add_to_Cart);
            holder.tv_quantity=(TextView)view.findViewById(R.id.quantity);
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
            Picasso.with(getActivity()).load(image).placeholder(R.drawable.ic_menu_camera)
                    .into(holder.item_img);
            System.out.println("Image View:" + image);
            final float price1= Float.parseFloat(holder.txt_price.getText().toString().replaceAll("[$]",""));
            String[] quantity = {"25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, quantity);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            holder.spinner.setAdapter(adapter);

            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selected = parent.getItemAtPosition(position).toString();
                    holder.tv_quantity.setTag(selected);
                    float subtotal=(price1)*(Integer.parseInt(selected));
                    holder.tv_subtotal.setText(String .format(String.valueOf(subtotal)));
                    holder.btn_add_cart.setTag(subtotal);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
                }

            });
            holder.btn_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count=Integer.parseInt(holder.tv_quantity.getTag().toString());
                    float subtotal1=Float.parseFloat(holder.btn_add_cart.getTag().toString());
                    // Log.d("insert data: " + myDb.insertData(myHolder.tvName.getText().toString(),myHolder.tv_subtotal.getText().toString(),myHolder.spinner1.getSelectedItemPosition(),"null"));
                    myDb.insertData(item,String.valueOf(subtotal1), count,"roshan");
                    Toast.makeText(getActivity(), "" + item + " Added to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    //Date picker

    Calendar c = Calendar.getInstance();
    final int mYear = dateAndTime.get(Calendar.YEAR);
    final int mMonth = dateAndTime.get(Calendar.MONTH);
    final int mDay = dateAndTime.get(Calendar.DAY_OF_MONTH);



    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Favourite");

    }
}
