package com.cs307.evant.evant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static com.cs307.evant.evant.MainActivity.db;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // back button
        Button backButton = (Button) findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               Intent intent = new Intent(Settings.this, MainActivity.class);
               startActivity(intent);
            }
        });

        // sign out
        Button signoutButton = (Button) findViewById(R.id.SignOutButton);
        signoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                db.signOut();
                SQLiteOpenHelper DatabaseHelper = new DataHelp(Settings.this);
                SQLiteDatabase db = DatabaseHelper.getReadableDatabase();
                //cursor = db.query("LOGINDATA", new String[]{"CAT", "PATH","CNT","ROTATE","IMAGE"}, null, null, null, null, "_id DESC");
                db.delete("LOGINDATA", null, null);
                ContentValues cv = new ContentValues();

                cv.put("USER","");
                cv.put("PASS", "");
                cv.put("LOGGED",0);
                db.insert("LOGINDATA",null,cv);

                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
