package com.example.roshan.appybites.Chef;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.roshan.appybites.BadgeDrawable;
import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.example.roshan.appybites.Http_Handler.HttpHandler;
import com.example.roshan.appybites.Model_Class.Catering_item;
import com.example.roshan.appybites.Nav_view_menu.Cart;
import com.example.roshan.appybites.R;
import com.example.roshan.appybites.RecyclerView_Adapter.Catering_Adapter;
import com.example.roshan.appybites.RecyclerView_Adapter.Chef_detail_adapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.id;
import static com.example.roshan.appybites.R.id.chef_id;

//import static com.example.roshan.appybites.MainActivity.setBadgeCount;

public class Chef_description extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private TextView txt_chef_id,txt_Chef_name,txt_Chef_desc,txt_chefname;
    private ImageView chef_image;
    private DbHandler myDb;
    private RecyclerView recyclerView;
    private Chef_detail_adapter mAdapter;
    ProgressDialog pdLoading;
    String chef_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_chef_nirjala);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        pdLoading= new ProgressDialog(this);
        txt_chefname=(TextView)findViewById(R.id.txt_chef_name);
        txt_Chef_desc=(TextView)findViewById(R.id.chef_desc);
        txt_chef_id=(TextView)findViewById(R.id.txt_chef_id);
        txt_Chef_name=(TextView)findViewById(R.id.txt_chef_name);
        chef_image=(ImageView)findViewById(R.id.spot_image);
        String chef_name = getIntent().getExtras().getString("chef_name");
        String chef_desc=getIntent().getExtras().getString("chef_description");
        chef_id=getIntent().getExtras().getString("chef_id");
        txt_Chef_desc.setText(chef_desc);
        txt_chefname.setText("About "+chef_name);
        txt_Chef_name.setText(chef_name);
        txt_chef_id.setText(chef_id);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(chef_name);
        myDb=new DbHandler(getApplicationContext());
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorAccent));
        String img_id;
        img_id = getIntent().getExtras().getString("IMAGE");
        Picasso.with(getApplicationContext()).load(img_id).placeholder(R.drawable.ic_menu_camera)
                .into(chef_image);
        new JsonTask().execute();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;

            default:
                return true;
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }
    private class JsonTask extends AsyncTask<String,Void,String> {

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
            String jsonStr = sh.makeServiceCall("http://appybite.com/index.php?route=api/food/productBychef&chef_id=1&token=F46AxXU0XAhBZWbN7gIbEB81ZZHfJW2Q");
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
                       String chef_name=  catering_item.setP_name(j.optString("name"));
                        catering_item.setP_desc(j.optString("description"));
                         catering_item.setP_price(j.optString("pirce"));
                        catering_item.setP_image(j.optString("image"));
                      String menu=    catering_item.setMenu_cate(j.optString("menu_cate"));
                        System.out.println("Menu Cate: "+chef_name);
                        //System.out.println("Insert: "+"inserting in searcview");
                      //  myDb.insertData(name,price,image);
                    }
                    recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
                    mAdapter = new Chef_detail_adapter(Chef_description.this, catering_items);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

}


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        Cursor cursor = myDb.viewData();
//        MenuItem itemCart = menu.findItem(R.id.action_settings);
//        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
//        setBadgeCount(this, icon, String.valueOf(cursor.getCount()));
//        invalidateOptionsMenu();
//        cursor.close();
//        return true;
//    }
//
//    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {
//
//        BadgeDrawable badge;
//
//        // Reuse drawable if possible
//        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
//        if (reuse != null && reuse instanceof BadgeDrawable) {
//            badge = (BadgeDrawable) reuse;
//        } else {
//            badge = new BadgeDrawable(context);
//        }
//
//        badge.setCount(count);
//        icon.mutate();
//        icon.setDrawableByLayerId(R.id.ic_badge, badge);
//    }
}
