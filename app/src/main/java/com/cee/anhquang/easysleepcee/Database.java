package com.cee.anhquang.easysleepcee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DAQ on 10/22/2017.
 */

public class Database extends SQLiteOpenHelper {

    //ten Database, ten Table, ten cac cot
    public static final String DATABASE_NAME ="User_Info.db";
    public static final String TABLE_NAME = "User_Info_table";
    public static final String COL_1 ="User_Name";
    public static final String COL_2 ="User_Pass";

    public Database(Context context) {
        super(context,DATABASE_NAME, null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tao bang
        //Cau truc lenh:
        //create table TABLE_NAME (COL_1 text primarykey, COL_2 text)
        db.execSQL("create table "+TABLE_NAME+" ("+COL_1+" text primary key, "+COL_2+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Upgrade table
        //Cau truc lenh
        //drop table if exists
        db.execSQL("drop table if exists" +TABLE_NAME);
        onCreate(db);
    }

    // Them du lieun - Register memmber
    public Boolean addData(String nameacc,String passacc){
        Boolean result = true;

        //Content value
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1,nameacc);
        cv.put(COL_2,passacc);
        long r = db.insert(TABLE_NAME,null,cv);
        if(r==-1){result = false;}
        return result;
    }

    //Dang nhap he thong FindData
    public Boolean findData(String namefind, String passfind){
        SQLiteDatabase db = this.getWritableDatabase();

        String SQLStr = "SELECT * FROM "+TABLE_NAME;
        Cursor cs = db.rawQuery(SQLStr,null);
        if(cs.moveToFirst()){
            do{
                String nameincursor = cs.getString(cs.getColumnIndex(COL_1));
                if (namefind.equals(nameincursor)){
                    String passincursor = cs.getString(cs.getColumnIndex(COL_2));
                    if (passfind.equals(passincursor)) {
                        return true;
                    }
                    else
                        return false;
                }
            }
            while (cs.moveToNext());
        }
        return false;
    }
}
