package com.example.roshan.appybites.RecyclerView_Adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.roshan.appybites.DatePickerFragment;
import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.example.roshan.appybites.Db_Handler.Fav_sql;
import com.example.roshan.appybites.Item_description;
import com.example.roshan.appybites.MainActivity;
import com.example.roshan.appybites.Model_Class.Catering_item;
import com.example.roshan.appybites.Model_Class.Constants;
import com.example.roshan.appybites.Our_Product.Catering;
import com.example.roshan.appybites.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static android.R.attr.bitmap;
import static android.support.v7.appcompat.R.id.image;

/**
 * Created by roshan on 3/18/17.
 */

public class Catering_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private View view;
    private LayoutInflater inflater;
    private DbHandler myDb;
    private Fav_sql myfav;
    public DatePickerFragment f_manager;
    private DatePickerFragment.OnItemClickListener listener;
    public int dateTime,dateyear,datemonth;
    Calendar dateAndTime = Calendar.getInstance();

    private static final String DIALOG_DATE = "MainActivity.DateDialog";
    SharedPreferences sharedPreferences;
    List<Catering_item> catering_items= Collections.emptyList();
    Catering_item current;
    int currentPos=0;
    public Catering_Adapter(Context context, List<Catering_item> catering_items) {
        this.context=context;
        if (context != null) {
     inflater= LayoutInflater.from(context);
        }

        this.catering_items=catering_items;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.fragment_catering, parent,false);
        MyHolder holder=new MyHolder(view);
        myDb=new DbHandler(context);
        myfav=new Fav_sql(context);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
// Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder= (MyHolder) holder;
        myHolder.btn_heart_off.setTag(position);
        final Catering_item current=catering_items.get(position);
        myHolder.tvName.setText(current.getP_name());
        myHolder.txt_price.setText(current.getP_price());
        final DatePickerDialog.OnDateSetListener startdate=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, monthOfYear);
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

       // myHolder.tvingredient.setText(current.getIngredient());
        myHolder.tvdescription.setText(current.getP_desc());
       // myHolder.tvchef.setText(current.getChef_name());
     //  final String sub_price=myHolder.txt_price.getText().toString().replaceAll("[$]","");
       // myHolder.txt_price.setText("$ " + sub_price);
        myHolder.txt_price.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
     //   myHolder.btn_heart_off.setImageResource(R.drawable.heart_off);
       final float price= Float.parseFloat(myHolder.txt_price.getText().toString().replaceAll("[$]",""));

        //cart button


        myHolder.btn_heart_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHolder.btn_heart_off.setVisibility(View.GONE);
                myHolder.btn_heart_on.setVisibility(View.VISIBLE);

                //.insert imgae to data sqlite
                myfav.insertData(current.getP_name(),current.getP_price(), current.getChef_image());
            }
        });
        myHolder.btn_heart_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHolder.btn_heart_off.setVisibility(View.VISIBLE);
                myHolder.btn_heart_on.setVisibility(View.GONE);
                myfav.deleteData(current.getP_name());
            }
        });


        
         String[] quantity = {"25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, quantity);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        myHolder.spinner1.setAdapter(adapter);
       // final String count= String.valueOf(myHolder.spinner1.getSelectedItem());
        // load image into imageview using glide
      Picasso.with(context).load(current.getP_image())
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_camera)
                .into(myHolder.imageicon);
        Picasso.with(context).load(current.getChef_image())
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_camera)
                .into(myHolder.chef_img);
        System.out.println("Chef image: "+myHolder.chef_img);
        myHolder.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getItemAtPosition(position).toString();
                myHolder.tvquantity.setTag(selected);


                    float subtotal=(price)*(Integer.parseInt(selected));
                    myHolder.tv_subtotal.setText(String .format(String.valueOf(subtotal)));
                    myHolder.btn_Add_to_cart.setTag(subtotal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
            }

        });

        myHolder.btn_Add_to_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final int count=Integer.parseInt(myHolder.tvquantity.getTag().toString());
                final float subtotal1=Float.parseFloat(myHolder.btn_Add_to_cart.getTag().toString());


            //datepicker
                Calendar c = Calendar.getInstance();
                final int mYear = dateAndTime.get(Calendar.YEAR);
                final int mMonth = dateAndTime.get(Calendar.MONTH);
                final int mDay = dateAndTime.get(Calendar.DAY_OF_MONTH);
                System.out.println("the selected " + mDay);

                DatePickerDialog dialog = new DatePickerDialog(context,
                        startdate, mYear, mMonth, mDay);
                dialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences prefs=context.getSharedPreferences("DATE_AND_STUFF",Context.MODE_PRIVATE);
                        String date=prefs.getString("date",null);
                        myHolder.tvingredient.setText(date);
                        System.out.println("Data :" +myDb.viewData());
                        myDb.insertData(current.getP_name(),String.valueOf(subtotal1), count,date);

                        Toast.makeText(context, "" + current.getP_name() + " Added to cart", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
              //  myHolder.tvingredient.setText(mDay);
               // Log.d("insert data: " + myDb.insertData(myHolder.tvName.getText().toString(),myHolder.tv_subtotal.getText().toString(),myHolder.spinner1.getSelectedItemPosition(),"null"));
             //
            }
            private DatePickerDialog.OnDateSetListener startdate = new DatePickerDialog.OnDateSetListener() {

                // onDateSet method
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String date_selected = String.valueOf(monthOfYear+1)+" /"+String.valueOf(dayOfMonth)+" /"+String.valueOf(year);
                    Toast.makeText(context, "Selected start Date is "+date_selected, Toast.LENGTH_SHORT).show();
                    SharedPreferences prefs = context.getSharedPreferences("DATE_AND_STUFF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("date", date_selected);
                    editor.commit();

                }
            };
        });

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Catering_item current=catering_items.get(position);
                Intent i=new Intent(context, Item_description.class);
                i.putExtra("item_name",current.getP_name());
                i.putExtra("item_price",current.getP_price());
                i.putExtra("item_description",current.getP_desc());
                i.putExtra("chef_image",current.getChef_image());
                i.putExtra("ingredient",current.getP_ingredients());
                i.putExtra("item_image",current.getP_image());
                Bundle bundle=new Bundle();
                bundle.putString("IMAGE",current.getP_image());

                bundle.putString("Chef_Image",current.getChef_image());
                i.putExtras(bundle);
                System.out.println("Image file: "+bundle);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return catering_items.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{

        private ImageView imageicon,chef_img;
        private  TextView tvName,tv_subtotal,tvprice,txt_price,tvquantity,tvdescription,tvingredient,tvchef;
        private Spinner spinner1;
        private Button btn_Add_to_cart;
        private ImageView btn_heart_off,btn_heart_on;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            btn_Add_to_cart=(Button)itemView.findViewById(R.id.btn_Add_to_Cart);
            imageicon=(ImageView)itemView.findViewById(R.id.item_image);
            chef_img=(ImageView)itemView.findViewById(R.id.chef_image);
            tvName=(TextView)itemView.findViewById(R.id.txt_item_name);
            tvdescription=(TextView)itemView.findViewById(R.id.txt_description);
            tvchef=(TextView)itemView.findViewById(R.id.txt_chef);
            tvingredient=(TextView)itemView.findViewById(R.id.txt_ingredient);
            tvprice=(TextView)itemView.findViewById(R.id.txt_foodPrice);
            tvquantity=(TextView)itemView.findViewById(R.id.quantity);
            txt_price=(TextView)itemView.findViewById(R.id.txt_price);
            tv_subtotal=(TextView)itemView.findViewById(R.id.txt_id);
            spinner1=(Spinner)itemView.findViewById(R.id.spinner);
            btn_heart_off=(ImageView) itemView.findViewById(R.id.btn_heart_off);
            btn_heart_on=(ImageView) itemView.findViewById(R.id.btn_heart_on);

        }

    }
    class mDateSetListener  implements DatePickerDialog.OnDateSetListener {

        private TextView v;

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
//            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
//            v.setText(new StringBuilder()
//                    // Month is 0 based so add 1
//                    .append(mMonth + 1).append("/").append(mDay).append("/")
//                    .append(mYear).append(" "));


        }
    }
}
