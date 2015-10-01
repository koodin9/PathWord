package com.welshcora.pathword;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DbAdapter {


    public static final String KEY_NAME1 = "word";
    public static final String KEY_NAME2 = "description";
    public static final String KEY_ROWID = "_id";



    private static final String TAG = "DbAdapter";

    private DatabaseHelper mDbHelper;

    private SQLiteDatabase mDb;

    private static final String DATABASE_CREATE =
            "create table engKor (_id integer primary key autoincrement, "
                    + "word text not null, description text not null);";

    private static final String DATABASE_NAME = "Test6.db";
    private static final String DATABASE_TABLE = "engKor";

    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            //super(context, "/mnt/sdcard/" + DATABASE_NAME, null, DATABASE_VERSION);
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL("create table wordbook (word text primary key not null, description text not null, date text not null);");
            Log.d(TAG,"db create");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    public DbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public ArrayList<String> SearchWord(String word, int offset){
        ArrayList<String> str = new ArrayList<String>();
        Cursor cursor = mDb.rawQuery("select word,description from engKor where word like '"+word+"%' order by word LIMIT 10 OFFSET "+offset , null);
        while(cursor.moveToNext()) {
            Log.v("단어이름", cursor.getString(0));
            Log.v("단어설명", cursor.getString(1));
            str.add(cursor.getString(0));
            str.add(cursor.getString(1));
        }
        return str;
    }

    public ArrayList<String> SelectWordBook(String type){
        ArrayList<String> str = new ArrayList<String>();
        if(type.equals("recent")){
            Cursor cursor = mDb.rawQuery("select date,word,description from wordbook order by date desc" , null);
            while(cursor.moveToNext()) {
                Log.v("단어장 날짜", cursor.getString(0));
                Log.v("단어장 단어", cursor.getString(1));
                Log.v("단어장 설명", cursor.getString(2));
                str.add(cursor.getString(0));
                str.add(cursor.getString(1));
                str.add(cursor.getString(2));
            }
        } else if (type.equals("alphabet")) {
            Cursor cursor = mDb.rawQuery("select date,word,description from wordbook order by word" , null);
            while(cursor.moveToNext()) {
                Log.v("단어장 날짜", cursor.getString(0));
                Log.v("단어장 단어", cursor.getString(1));
                Log.v("단어장 설명", cursor.getString(2));
                str.add(cursor.getString(0));
                str.add(cursor.getString(1));
                str.add(cursor.getString(2));
            }
        }

        return str;
    }

    public boolean addWordAtBook(String word, String description, String date){
        try {
            mDb.execSQL("insert into wordbook values('" + word + "', '" + description + "', '" + date + "')");
            return true;
        } catch (SQLiteConstraintException e){
            return false;
        }
    }

    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createData(String key_name1, String key_name2) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME1, key_name1);
        initialValues.put(KEY_NAME2, key_name2);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
} 

