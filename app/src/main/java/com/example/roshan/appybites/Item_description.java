package com.example.roshan.appybites;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.squareup.picasso.Picasso;

public class Item_description extends AppCompatActivity {
    private TextView text_item,txt_desc,txt_price,txt_chef,txt_ingredient;
    private ImageView imageView1,chef_img;
    private Intent intent;
    private Bundle bundle;
    private TextView ui_hot = null;
    private DbHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        myDb=new DbHandler(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        chef_img=(ImageView)findViewById(R.id.chef_image);
        text_item=(TextView)findViewById(R.id.txt_item_name);
       // txt_chef=(TextView)findViewById(R.id.txt_chef);
        txt_desc=(TextView)findViewById(R.id.txt_description);
        txt_price=(TextView)findViewById(R.id.txt_price);
        txt_ingredient=(TextView)findViewById(R.id.txt_igredient);
        imageView1=(ImageView)findViewById(R.id.imageView);

        String img_id;
           img_id = getIntent().getExtras().getString("IMAGE");
            Picasso.with(getApplicationContext()).load(img_id).placeholder(R.drawable.ic_menu_camera)
        .into(imageView1);

        String chef_img1=getIntent().getExtras().getString("Chef_Image");
        Picasso.with(getApplicationContext()).load(chef_img1).placeholder(R.drawable.ic_menu_camera)
                .into(chef_img);
       // ImageAdapter imageAdapter = new ImageAdapter(this);
     //   Bitmap img = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        String item_name = getIntent().getExtras().getString("item_name");
        String item_price=getIntent().getExtras().getString("item_price");
      //  String chef_name=getIntent().getExtras().getString("chef_name");
        String ingredient=getIntent().getExtras().getString("ingredient");
     //   String image=getIntent().getExtras().getString("item_image");
        String desc=getIntent().getExtras().getString("item_description");
//        txt_chef.setText(chef_name);
        text_item.setText(item_name);
        txt_ingredient.setText(ingredient);
        txt_price.setText("$ "+item_price);
        txt_desc.setText(desc);
       // System.out.println("Load Image : " +img_id );

        actionBar.setTitle(item_name);
        actionBar.setIcon(R.drawable.appy);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        actionBar.setDisplayUseLogoEnabled(true);
       // imageView.setImageBitmap(img);
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
