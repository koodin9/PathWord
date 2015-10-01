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
            db.execSQL("create table test (date text primary key not null, question text not null, correct text not null, incorrect text not null, type text not null, rank text not null);");
            db.execSQL("create table wrongnote (date text primary key not null, type text not null, word text not null);");
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
            str.add(cursor.getString(0));
            str.add(cursor.getString(1));
        }
        return str;
    }
    public ArrayList<String> SearchWord2(String word){//단어장 다이얼로그에서 쓰임 루트 테스트에도
        ArrayList<String> str = new ArrayList<String>();
        Cursor cursor = mDb.rawQuery("select description from engKor where word = '"+word+"'" , null);
        while(cursor.moveToNext()) {
            str.add(cursor.getString(0));
        }
        return str;
    }

    public int SerchId(String word){
        Cursor cursor = mDb.rawQuery("select _id from engKor where word = '"+word+"'",null);
        int temp = -1;
        while(cursor.moveToNext()){
            temp = cursor.getInt(0);
        }
        return temp;
    }

    public ArrayList<String> SearchWord3( int random[], String type){//단어 테스트에 쓰임
        ArrayList<String> str = new ArrayList<String>();
        if(type.equals("wordtest")){
            Cursor cursor = mDb.rawQuery("select description from engKor where _id = "+random[0]+" or _id = "+random[1]+" or _id = "+random[2]+" or _id = "+random[3] , null);
            while(cursor.moveToNext()) {
                str.add(cursor.getString(0));
            }
        } else {
            Cursor cursor = mDb.rawQuery("select description from engKor where _id = "+random[0]+" or _id = "+random[1]+" or _id = "+random[2]+" or _id = "+random[3]+" or _id = "+random[4] , null);
            while(cursor.moveToNext()) {
                str.add(cursor.getString(0));
            }
        }

        return str;
    }

    public int totalCount(){
        Cursor cursor = mDb.rawQuery("select count(word) from engKor" , null);
        int temp = 0;
        while(cursor.moveToNext()) {
            temp = cursor.getInt(0);
        }
        return temp;
    }

    public ArrayList<String> SelectWordBook(String type){
        ArrayList<String> str = new ArrayList<String>();
        if(type.equals("recent")){
            Cursor cursor = mDb.rawQuery("select date,word,description from wordbook order by date desc" , null);
            while(cursor.moveToNext()) {
                str.add(cursor.getString(0));
                str.add(cursor.getString(1));
                str.add(cursor.getString(2));
            }
        } else if (type.equals("alphabet")) {
            Cursor cursor = mDb.rawQuery("select date,word,description from wordbook order by word" , null);
            while(cursor.moveToNext()) {
                str.add(cursor.getString(0));
                str.add(cursor.getString(1));
                str.add(cursor.getString(2));
            }
        }
        return str;
    }
    public ArrayList<String> SelectWordBook2(ArrayList<String> victimword){
        ArrayList<String> str = new ArrayList<String>();
        for(int i = 0; i < victimword.size(); i++){
            Cursor cursor = mDb.rawQuery("select date,word,description from wordbook where word = '"+victimword.get(i)+"'" , null);
            while(cursor.moveToNext()) {
                str.add(cursor.getString(0));
                str.add(cursor.getString(1));
                str.add(cursor.getString(2));
            }
        }
        return str;
    }

    public void DeletedWordBook(String word){
        mDb.execSQL("delete from wordbook where word = '"+word+"'");
    }

    public boolean addWordAtBook(String word, String description, String date){
        try {
            mDb.execSQL("insert into wordbook values('" + word + "', '" + description + "', '" + date + "')");
            return true;
        } catch (SQLiteConstraintException e){
            return false;
        }
    }

    public void reflectTest(String date, int question, int correct, int incorrect, String type, int rank, ArrayList<String> wrongWord){
        mDb.execSQL("insert into test values('"+date+"', '"+question+"', '"+correct+"', '"+incorrect+"', '"+type+"', '"+rank+"')");
        String temp = "";
        for(int i = 0; i < wrongWord.size(); i++){
            temp += wrongWord.get(i)+", ";
        }
        temp = temp.substring(0, temp.length()-2);
        mDb.execSQL("insert into wrongnote values('"+date+"', '"+type+"', '"+temp+"')");
    }

    public ArrayList<String> getTest(){
        ArrayList<String> str = new ArrayList<String>();
        Cursor cursor = mDb.rawQuery("select * from test" , null);
        while(cursor.moveToNext()) {
            str.add(cursor.getString(0));
            str.add(cursor.getString(1));
            str.add(cursor.getString(2));
            str.add(cursor.getString(3));
            str.add(cursor.getString(4));
            str.add(cursor.getString(5));
        }
        return str;
    }

    public ArrayList<String> getWrongNote(){
        ArrayList<String> str = new ArrayList<String>();
        Cursor cursor = mDb.rawQuery("select * from wrongnote" , null);
        while(cursor.moveToNext()) {
            str.add(cursor.getString(0));
            str.add(cursor.getString(1));
            str.add(cursor.getString(2));
        }
        return str;
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

