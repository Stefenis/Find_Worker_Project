package com.project.find_worker_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String database_Name = "UserLocation.db";
    public DBHelper(@Nullable Context context) {
        super(context , "UserLocation.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userLocation(id INTEGER PRIMARY KEY AUTOINCREMENT, longtitude TEXT , latitude TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists userLocation");

    }

    public Boolean insertlocationdata(double latitude, double longtitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("latitude",latitude);
        contentValues.put("longtitude",longtitude);
        long result = db.insert("userLocation",null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
