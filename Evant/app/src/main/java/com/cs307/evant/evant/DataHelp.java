package com.cs307.evant.evant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by avi12 on 3/4/2018.
 */
public class DataHelp extends SQLiteOpenHelper
{
    private static final String Dname = "EvantData";
    private static final int Dversion = 1;

    DataHelp(Context cont)
    {
        super(cont,Dname,null,Dversion);
    }

    @Override
    public void onCreate(SQLiteDatabase tp)
    {
        updateMyDatabase(tp,0,Dversion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE LOGINDATA (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "USER TEXT, "
                    + "PASS TEXT, "
                    + "LOGGED INT, "
                    + "XTR TEXT) ;");
        }

        ContentValues cv = new ContentValues();
        //cv.put("USER","avi7agarwal");
        //cv.put("PASS", "Hello");
        cv.put("LOGGED",0);
        db.insert("LOGINDATA",null,cv);
    }
}
