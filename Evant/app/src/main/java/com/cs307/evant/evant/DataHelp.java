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
            db.execSQL("CREATE TABLE ITEMDATA (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CAT TEXT, "
                    + "NAME TEXT, "
                    + "PATH TEXT, "
                    + "INFO TEXT, "
                    + "IMAGE BLOB, "
                    + "FAV INT, "
                    + "ROTATE INT"
                    + "MARK INT);");
            db.execSQL("CREATE TABLE CATDATA (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CAT TEXT, "
                    + "PATH TEXT, "
                    + "CNT INT, "
                    + "ROTATE INT, "
                    + "IMAGE BLOB, "
                    + "XTR TEXT) ;");
        }

        ContentValues cv = new ContentValues();
        cv.put("CAT","Outfits");
        cv.put("ROTATE", 99);
        cv.put("CNT",0);
        db.insert("CATDATA",null,cv);
    }
}
