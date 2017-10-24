package com.example.roshan.appybites.Db_Handler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by roshan on 5/13/17.
 */

public class Fav_sql extends SQLiteOpenHelper {
    public static final String database_name="AppyBite_fav";

    //create table
    public static final String table_name="favourite";
    public static final String col_1=" _id";
    public static final String col_2="item_name";
    public static final String col_3="item_price";
    public static final String col_4="image";

    public Fav_sql(Context context) {
        super(context, database_name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
            db.execSQL("create table " + table_name + "( _id integer primary key autoincrement ,item_name text,item_price float,image blob)");

        }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists" + table_name);
        // db.execSQL("ALTER TABLE "+ table_name +" ADD "+ col_1 +" INTEGER PRIMARY KEY AUTOINCREMENT");
        onCreate(db);
    }

    public void insertData(String item_name, String item_price, String image){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(col_2,item_name);
        values.put(col_3,item_price);
        values.put(col_4, image);
        int u = db.update("favourite", values, "item_name=?", new String[]{item_name});
        if (u == 0) {
            db.insertWithOnConflict("favourite", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public void deleteData(String item_name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_name,"item_name=?",new String[] {item_name});
    }

//    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
//        return outputStream.toByteArray();
//    }
    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        //Cursor cr=db.rawQuery("select distinct item_name,item_price,quantity from " + table_name,null);
        Cursor cr =  db.rawQuery( "select _id as _id,  item_name, item_price, image from favourite",null);

        if (cr != null) {
            cr.moveToFirst();
        }
          Log.d("Cursor Size ", cr.getCount() +"");
        cr.getCount();
        return cr;

    }
}
