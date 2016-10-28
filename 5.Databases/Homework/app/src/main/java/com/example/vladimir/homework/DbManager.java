package com.example.vladimir.homework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by Vladimir on 29-Sep-16.
 */
public class DbManager extends SQLiteOpenHelper {
    private static DbManager mInstance = null;

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String ITEMS_TABLE = "Items";
    private static final String MAIN_TEXT = "main_text";
    private static final String SUB_TEXT = "sub_text";

    public static DbManager getInstance(Context ctx){
        if(mInstance==null){
            mInstance = new DbManager(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void addItems(int items){
        SQLiteDatabase db = this.getWritableDatabase();

        for(int i = 0 ;i<items;i++){
            ContentValues values = new ContentValues();
            values.put(MAIN_TEXT,"Main Text");
            values.put(SUB_TEXT,"Sub Text");

            db.insert(ITEMS_TABLE,null,values);
        }

    }

    public Cursor getAllElements(){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = getReadableDatabase();

        String[] sqlSelect = {MAIN_TEXT,SUB_TEXT};
        qb.setTables(ITEMS_TABLE);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        c.moveToFirst();
        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ITEMS_TABLE+" ("+MAIN_TEXT+" TEXT, "+SUB_TEXT+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
