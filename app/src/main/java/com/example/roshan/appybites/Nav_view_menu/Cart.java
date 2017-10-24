package com.example.roshan.appybites.Nav_view_menu;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.example.roshan.appybites.LoginActivity;
import com.example.roshan.appybites.MainActivity;
import com.example.roshan.appybites.Our_Product.Catering;
import com.example.roshan.appybites.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.roshan.appybites.Db_Handler.DbHandler.table_name;


/**
 * A simple {@link Fragment} subclass.
 */

public class Cart extends Fragment {
    private View view;
    private DbHandler myDb;
    private ListView lv;
    private Button btn_checkout;
    private TextView txt_total,txt_subtotal,txt_vat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.cart_list_view, container, false);
        lv=(ListView)view.findViewById(R.id.detail_list_view);
        btn_checkout=(Button)view.findViewById(R.id.btn_Checkout);
        txt_total = (TextView)view.findViewById(R.id.txt_total);
        txt_subtotal=(TextView)view.findViewById(R.id.txt_Subtotal);
        txt_vat = (TextView)view.findViewById(R.id.txt_vat);
        myDb=new DbHandler(getActivity());
             //cursor activity
        final Cursor cursor = myDb.viewData();
        String[] from = {myDb.col_2,myDb.col_3,myDb.col_4,myDb.col_5};
        int[] to = {R.id.txt_item_name, R.id.foodPrice,R.id.spinner1,R.id.table_no};
        final cursor_adapter cd = new cursor_adapter(view.getContext(), R.layout.fragment_cart, cursor, from, to, 0);
        lv.setAdapter(cd);
        myDb.deleteDataSingle();
        setHasOptionsMenu(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isVisible()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // This code will always run on the UI thread, therefore is safe to modify UI elements.
                            Cursor c = myDb.total();
                            System.out.println("total "+ myDb.total());
                            float sub_total = c.getFloat(c.getColumnIndex("total_price"));
                            double vat = sub_total * 0.13;
                            double total = sub_total + vat;
                            txt_subtotal.setText(String.format("Sub Total : " + sub_total));
                            txt_vat.setText(String.format("Vat : %.2f ", vat));
                            txt_total.setText(String.format("Total : %.2f", total));
                        }
                    });
                }
            }
        },0, 1000);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.viewData();
                if (cursor.moveToFirst()){
                    do {
                        String itemname=cursor.getString(cursor.getColumnIndex(myDb.col_2));
                        String price=cursor.getString(cursor.getColumnIndex(myDb.col_3));
                        String quantity=cursor.getString(cursor.getColumnIndex(myDb.col_4));
                        String table_no=cursor.getString(cursor.getColumnIndex(myDb.col_5));
                        // new background(getActivity()).execute("cart",itemname,price,quantity,table_no);
                        // Toast.makeText(getActivity(),"added to ordered list", Toast.LENGTH_SHORT).show();
                    }while (cursor.moveToNext());

                }
                startActivity(new Intent(getActivity(), LoginActivity.class));
                cursor.close();
                cd.notifyDataSetChanged();
            }
        });
/*

//                Cursor cursor = myDb.viewData();
//                if (cursor.moveToFirst()){
//                    do {
//                        String itemname=cursor.getString(cursor.getColumnIndex(myDb.col_2));
//                        String price=cursor.getString(cursor.getColumnIndex(myDb.col_3));
//                        String quantity=cursor.getString(cursor.getColumnIndex(myDb.col_4));
//                        String table_no=cursor.getString(cursor.getColumnIndex(myDb.col_5));
//                        // new background(getActivity()).execute("cart",itemname,price,quantity,table_no);
//                        // Toast.makeText(getActivity(),"added to ordered list", Toast.LENGTH_SHORT).show();
//                    }while (cursor.moveToNext());
//                }
 */
        cursor.requery();
        cd.notifyDataSetChanged();
        setHasOptionsMenu(true);
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               checkOutItem(parent,position);
           }
       });

//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Catering()).commit();
//            }
//        });
//
        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.main, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//
//            Cursor cursor = myDb.viewData();
//            if (cursor.moveToFirst()){
//                do {
//                    String itemname=cursor.getString(cursor.getColumnIndex(myDb.col_2));
//                    String price=cursor.getString(cursor.getColumnIndex(myDb.col_3));
//                    String quantity=cursor.getString(cursor.getColumnIndex(myDb.col_4));
//                   String table_no=cursor.getString(cursor.getColumnIndex(myDb.col_5));
//                   // new background(getActivity()).execute("cart",itemname,price,quantity,table_no);
//                    Toast.makeText(getActivity(),""+item+" added to ordered list", Toast.LENGTH_SHORT).show();
//                }while (cursor.moveToNext());
//            }
//
//            myDb.deletedata(table_name);
//            startActivity(new Intent(getActivity(), MainActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//
//    }

    public void checkOutItem(AdapterView<?> parent,int position)
    {
        Cursor cursor = null;
        cursor = (Cursor) parent.getItemAtPosition(position);
//        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
//        animation1.setDuration(1000);
//        view.startAnimation(animation1);
        String item=cursor.getString(cursor.getColumnIndex(myDb.col_2));
        String price=cursor.getString(cursor.getColumnIndex(myDb.col_3));
        String quantity=cursor.getString(cursor.getColumnIndex(myDb.col_4));
        String table_no=cursor.getString(cursor.getColumnIndex(myDb.col_5));
       // new background(getActivity()).execute("cart",item,price,quantity,table_no);
    }

  public  class cursor_adapter extends SimpleCursorAdapter{
    LayoutInflater inflater;
    private int layout;


      public cursor_adapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
          super(context, layout, c, from, to, flags);
      }

      public class ViewHolder{
          private Spinner spinner;
          private TextView txt_item,txt_price,txt_quantity,txt_table;
          private Button btn_delete;
      }
      public View newView(Context _context, Cursor _cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_cart, parent, false);
         ViewHolder holder=new ViewHolder();
          view.setTag(holder);
          holder.spinner=(Spinner)view.findViewById(R.id.spinner);
          holder.txt_item=(TextView)view.findViewById(R.id.txt_item_name);
          holder.txt_price=(TextView)view.findViewById(R.id.foodPrice);
          holder.txt_quantity=(TextView)view.findViewById(R.id.spinner1);
          holder.txt_table=(TextView)view.findViewById(R.id.table_no);
          holder.btn_delete=(Button)view.findViewById(R.id.btn_delete);

        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        super.bindView(view, context, cursor);
        final ViewHolder holder = (ViewHolder) view.getTag();
        final String item=cursor.getString(cursor.getColumnIndex(myDb.col_2));
        holder.txt_item.setText(item);
        final String price=cursor.getString(cursor.getColumnIndex(myDb.col_3));
        holder. txt_price.setText(price);
        final String c_quantity=cursor.getString(cursor.getColumnIndex(myDb.col_4));
        holder.txt_quantity.setText(c_quantity);
        final String table_no=cursor.getString(cursor.getColumnIndex(myDb.col_5));
        holder.txt_table.setText(table_no);
        String[] quantity = {holder.txt_quantity.getText().toString(),"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, quantity);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        holder. spinner.setAdapter(adapter);
        holder.txt_quantity.setVisibility(View.GONE);
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(400);
                v.startAnimation(animation1);
                myDb.deleteData(item);
                Toast.makeText(getActivity(), "Item "+item+" deleted from cart", Toast.LENGTH_SHORT).show();
                cursor.requery();
                notifyDataSetChanged();
            }
        });
       holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String count= parent.getItemAtPosition(position).toString();;
                    float price1=(Float.parseFloat(holder.txt_price.getText().toString()));
                    int pre_quantity=(Integer.parseInt(holder.txt_quantity.getText().toString()));
                    Float subtotal=(price1 / pre_quantity)*(Integer.parseInt(count));
                    holder.txt_quantity.setVisibility(View.GONE);
                    holder.txt_quantity.setText(count);
                    holder.txt_price.setText(String.format("%.02f",subtotal));
                    myDb.insertData(item, String.valueOf(subtotal), Integer.parseInt(count),table_no);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
            }

        });

    }

}

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Cart");

    }
}

