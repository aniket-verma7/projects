package com.personal.whatsappchatbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper
{
    public Database(@Nullable Context context)
    {
        super(context, "tambola.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table number (num integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
    public boolean insertNumber(int lst)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("num",lst);

        long x = db.insert("number",null,contentValues);

        if(x>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public ArrayList<Integer> getAllNumber()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> lstNumber = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from number",null);
        if(cursor!=null)
        {
            while (cursor.moveToNext()) {
                lstNumber.add(0, cursor.getInt(cursor.getColumnIndex("num")));
            }
        }
        else
        {
            return null;
        }
        return lstNumber;
    }

    public boolean deleteNumber()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long x = db.delete("number","num>0",null);

        if(x>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
