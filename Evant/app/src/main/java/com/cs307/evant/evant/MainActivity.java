package com.cs307.evant.evant;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /*test*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_main);

        Button mapButton = (Button) findViewById(R.id.mapButton);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapView.class);
                startActivity(intent);
            }
        });

        Button newButton = (Button) findViewById(R.id.createButton);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
                startActivity(intent);
            }
        });


        Button logButton = (Button) findViewById(R.id.loginButton);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Intent intent = new Intent(MainActivity.this, NewEventActivity.class);
                //startActivity(intent);

                AlertDialog.Builder addItem = new AlertDialog.Builder(MainActivity.this);
                addItem.setMessage("Please Login or Sign Up");
                addItem.setTitle("Welcome to Evant!");
                final Intent lpage = new Intent(MainActivity.this, loginPage.class);
                //startActivity(intent);
                View.OnClickListener login = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dispatchTakePictureIntent();
                    }
                };
                addItem.setPositiveButton("Sign Up!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface di, int id) {
                        //dispatchTakePictureIntent();
                    }
                });
                addItem.setNegativeButton("Login!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface di, int id) {
                        //ImageView iv = (ImageView) findViewById(R.id.testView);
                        //loadImagefromGallery();
                        startActivity(lpage);
                    }

                });
                addItem.create();
                addItem.show();
            }


        });


    }
}
