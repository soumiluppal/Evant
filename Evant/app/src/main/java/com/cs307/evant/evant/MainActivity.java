package com.cs307.evant.evant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    /*test*/
    static Database db = new Database();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            //load map
            Intent intent = new Intent(MainActivity.this, MapView.class);
            startActivity(intent);
        }else {
            //prompt login/signup
            AlertDialog.Builder addItem = new AlertDialog.Builder(MainActivity.this);
            addItem.setMessage("Please Login or Sign Up");
            addItem.setTitle("Welcome to Evant!");
            final Intent lpage = new Intent(MainActivity.this, loginPage.class);
            final Intent spage = new Intent(MainActivity.this, signup.class);
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
                    startActivity(spage);
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
    }

    protected void onResume() {
        super.onResume();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            //load map
            Intent intent = new Intent(MainActivity.this, MapView.class);
            startActivity(intent);
        }else {
            //prompt login/signup
            AlertDialog.Builder addItem = new AlertDialog.Builder(MainActivity.this);
            addItem.setMessage("Please Login or Sign Up");
            addItem.setTitle("Welcome to Evant!");
            final Intent lpage = new Intent(MainActivity.this, loginPage.class);
            final Intent spage = new Intent(MainActivity.this, signup.class);
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
                    startActivity(spage);
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
    }

    //TEMPORARY MAIN PAGE
    /*protected void onCreate(Bundle savedInstanceState) {
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

        Button listButton = (Button) findViewById(R.id.eventlistButton);

        listButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, catList.class);
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
                final Intent spage = new Intent(MainActivity.this, signup.class);
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
                        startActivity(spage);
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


    }*/

}
