package com.example.venturedive.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Venturedive on 4/11/2017.
 */

public class SQLliteHelper extends SQLiteOpenHelper implements DataStorage {


    private Context mContext;
    private String mDbName;

    public SQLliteHelper(Context mContext, String mDbName){
        super(mContext,mDbName,null,1);
    }

    @Override
    public ArrayList<Crime> Load() {
        ArrayList<Crime> crimes=new ArrayList<>();
        Cursor cursor=getReadableDatabase().query("Crime",null,null,null,null,null,null,null);
        Log.d("SQlite",cursor.getCount()+"");
        cursor.moveToFirst();
        while (true){
            boolean solved=true;
            if(cursor.getInt(cursor.getColumnIndex("solved"))==0)
                solved=false;
            Crime crime=new Crime(cursor.getString(cursor.getColumnIndex("title")),new Date(cursor.getLong(cursor.getColumnIndex("date"))),solved);
            crimes.add(crime);
            cursor.moveToNext();
            if(cursor.isAfterLast())
                break;
        }
        return crimes;
    }

    @Override
    public void Save(ArrayList<Crime> crimes) {
     getWritableDatabase().delete("Crime",null,null);
      for (Crime c:crimes)
          InsertCrime(c);
    }

    private boolean InsertCrime(Crime c){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
      //  cv.put("id",c.getId().toString());
        cv.put("title",c.getTitle());
        cv.put("solved",c.isSolved()?1:0);
        cv.put("date",c.getDate().getTime());
        db.insert("Crime",null,cv);
        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table Crime(id text primary key,title text,solved integer,date integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
