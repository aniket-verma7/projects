package com.example.lenden.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.lenden.MainActivity;
import com.example.lenden.userClass.Details;

import java.util.ArrayList;
import java.util.Currency;

public class dataHelper extends SQLiteOpenHelper
{
    private long sumIn = 0,sumOut = 0,diff=0;

    public dataHelper(@Nullable Context context) {

        super(context,"data.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table userData (id integer primary key autoincrement,name text,amount text,type text,mob text,currentDate date)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public boolean insertData(Details details)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues  = new ContentValues();

        contentValues.put("name",details.getName());
        contentValues.put("amount",details.getAmount());
        contentValues.put("type",details.getType());
        contentValues.put("mob",details.getMob());
        contentValues.put("currentDate",details.getLocalDate());

        long x = db.insert("userData",null,contentValues);

        if(x>0)
        {

            return true;
        }
            else return  false;
    }

    public boolean deleteData(Details details)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long x = db.delete("userData","id=?",new String[]{details.getId()+""});

        if(x>0)return true;
        else return false;
    }

    public boolean updateData(Details details)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",details.getName());
        contentValues.put("amount",details.getAmount());
        contentValues.put("type",details.getType());
        contentValues.put("mob",details.getMob());
        contentValues.put("currentDate",details.getLocalDate());

        long x = db.update("userData",contentValues,"id=?",new String[]{details.getId()+""});

        if (x>0) return true;
        else return false;
    }
   public long netAmount()
   {
       SQLiteDatabase db = this.getReadableDatabase();
       ArrayList<Details> lstDetails = new ArrayList<>();

       Cursor cursor = db.rawQuery("select amount,type from userData",null);

       while(cursor.moveToNext())
       {
           Details details = new Details();

           details.setAmount(cursor.getString(cursor.getColumnIndex("amount")));
           details.setType(cursor.getString(cursor.getColumnIndex("type")));
           lstDetails.add(details);
       }
       for(int i=0;i<lstDetails.size();i++)
       {
           String type ="";
           type = lstDetails.get(i).getType();
           if(type.equals("Credit"))
               sumIn  = sumIn + Integer.parseInt(lstDetails.get(i).getAmount());
           else
               sumOut = sumOut + Integer.parseInt(lstDetails.get(i).getAmount());
       }

        diff = sumIn - sumOut;
       return sumIn;
   }

   public long totalDebit(){return sumOut;}
   public long leftAmount(){return diff;}

    public ArrayList<Details> getAllDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Details> lstDetails = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from userData", null);


            while (cursor.moveToNext()) {
                Details details = new Details();

                details.setId(cursor.getInt(cursor.getColumnIndex("id")));
                details.setName(cursor.getString(cursor.getColumnIndex("name")));
                details.setAmount(cursor.getString(cursor.getColumnIndex("amount")));
                details.setType(cursor.getString(cursor.getColumnIndex("type")));
                details.setLocalDate(cursor.getString(cursor.getColumnIndex("currentDate")));
                details.setMob(cursor.getString(cursor.getColumnIndex("mob")));
                lstDetails.add(details);
            }
            return lstDetails ;
    }

}
