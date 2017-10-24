package com.example.roshan.appybites.Db_Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHandler extends SQLiteOpenHelper {
    public static final String database_name="AppyBite";

    //create table
    public static final String table_name="cart";
    public static final String col_1=" _id";
    public static final String col_2="item_name";
    public static final String col_3="item_price";
    public static final String col_4="quantity";
    public static final String col_5="user_name";


    public DbHandler(Context context) {
        super(context, database_name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + "( _id integer primary key autoincrement ,item_name text,item_price float,quantity integer,user_name text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists" + table_name);
       // db.execSQL("ALTER TABLE "+ table_name +" ADD "+ col_1 +" INTEGER PRIMARY KEY AUTOINCREMENT");
        onCreate(db);
    }

    public void insertData(String item_name,String item_price,int quantity,String user_name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(col_2,item_name);
        values.put(col_3,item_price);
        values.put(col_4, quantity);
        values.put(col_5,user_name);
        int u = db.update("cart", values, "item_name=?", new String[]{item_name});
        if (u == 0) {
            db.insertWithOnConflict("cart", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
    public Cursor viewData(){
        SQLiteDatabase db=this.getWritableDatabase();
        //Cursor cr=db.rawQuery("select distinct item_name,item_price,quantity from " + table_name,null);
     Cursor cr =  db.rawQuery( "select _id as _id,  item_name, item_price, quantity, user_name from cart ",null);

        if (cr != null) {
            cr.moveToFirst();
        }
      //  Log.d("Cursor Size ", cr.getCount() +"");
        cr.getCount();
        return cr;

    }

    public void deleteData(String item_name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_name,"item_name=?",new String[] {item_name});
    }

    public Cursor total(){
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor cr=db.rawQuery("select distinct item_name,item_price,quantity from " + table_name,null);
        Cursor cr =  db.rawQuery( "select sum(item_price) as total_price from cart ",null);

        if (cr != null) {
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cr));
            System.out.println("cursor Object "+cr);
            cr.moveToFirst();
        }
        return cr;
    }

    public void deleteDataSingle(){
        SQLiteDatabase db=this.getWritableDatabase();
       // db.delete(table_name,"quantity=0",new String[] {quantity});
       String delete="delete from cart where quantity=0";
        db.execSQL(delete);
    }
public void deletedata(String table_name) {
    SQLiteDatabase db=this.getWritableDatabase();
    String clearDBQuery = "delete from "+ table_name;
    db.execSQL(clearDBQuery);
}
}