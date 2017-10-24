package com.example.roshan.appybites;

import android.*;
import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roshan.appybites.Db_Handler.DbHandler;
import com.example.roshan.appybites.Db_Handler.Fav_sql;
import com.example.roshan.appybites.Db_Handler.Search_view;
import com.example.roshan.appybites.Nav_view_menu.About_Us;
import com.example.roshan.appybites.Nav_view_menu.Cart;
import com.example.roshan.appybites.Nav_view_menu.Contact;
import com.example.roshan.appybites.Nav_view_menu.Favourite;
import com.example.roshan.appybites.Nav_view_menu.Order_History;
import com.example.roshan.appybites.Nav_view_menu.Our_product;
import com.example.roshan.appybites.Nav_view_menu.Profile;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DbHandler myDb;
    private Search_view mydb;
    private Fav_sql myfav;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 0;
    boolean doubleBackToExitPressedOnce = false;
    private TextView ui_hot = null;
    private int mNotifCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DbHandler(getApplicationContext());
        mydb=new Search_view(getApplicationContext());
        myfav=new Fav_sql(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.appybite);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.invalidateOptionsMenu();
        actionBar.setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        {

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Our_product())
                    .commit();
        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // drawer.setDrawerIndicatorEnabled(false);
                invalidateOptionsMenu();

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //  getSupportActionBar().setTitle("AppyBite");
                invalidateOptionsMenu();
            }
        };

        drawer.setDrawerListener(toggle);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(true);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txt_user);
        txtProfileName.setText("roshan");

        //permission Granting

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);

                // MY_PERMISSIONS_REQUEST_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_STORAGE);

                    // MY_PERMISSIONS_REQUEST_STORAGE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Touch again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Cursor cursor = myDb.viewData();
        final View item = menu.findItem(R.id.action_settings).getActionView();
        ui_hot = (TextView) item.findViewById(R.id.hotlist_hot);
        ui_hot.setText(String.valueOf(cursor.getCount()));
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                   runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            // This code will always run on the UI thread, therefore is safe to modify UI elements.
//                        }
//                    });
//
//            }
//        },0, 1000);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Carts.class));
            }
        });
       // invalidateOptionsMenu();
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
            case R.id.action_search:
                this.finish();
                startActivity(new Intent(getApplicationContext(),SearchResultsActivity.class));
                return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.Our_services) {
            getSupportActionBar().setTitle("Home");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Our_product())
                    .commit();
            // Handle the attendance action
        } else if (id == R.id.profile) {
            getSupportActionBar().setTitle("Profile");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Profile()).addToBackStack(null)
                    .commit();

        } else if (id == R.id.Cart) {
            getSupportActionBar().setTitle("Cart");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Cart()).addToBackStack(null)
                    .commit();
        }
        else if (id == R.id.Favourites) {
            getSupportActionBar().setTitle("Favourite");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Favourite()).addToBackStack(null)
                    .commit();
        }
        else if (id == R.id.Order_Status) {
            getSupportActionBar().setTitle("Order History");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Order_History()).addToBackStack(null)
                    .commit();
        } else if (id == R.id.about) {
            getSupportActionBar().setTitle("About Us");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new About_Us()).addToBackStack(null)
                    .commit();
        }else if (id == R.id.contact) {
            getSupportActionBar().setTitle("Contact");
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Contact()).addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
