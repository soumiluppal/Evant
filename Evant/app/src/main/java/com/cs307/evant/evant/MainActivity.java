package com.cs307.evant.evant;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.splunk.mint.Mint;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    /*test*/
    static Database db = new Database();
    static boolean flag = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        Mint.initAndStartSession(this.getApplication(), "44191314");
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseAuth mAuth;
        SQLiteOpenHelper DatabaseHelper = new DataHelp(MainActivity.this);
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query("LOGINDATA", new String[]{"LOGGED", "PASS","USER"}, null, null, null, null, "_id DESC");
        cursor.moveToFirst();
        mAuth = FirebaseAuth.getInstance();

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            //load map
            if(flag == false) {
                flag = true;
                System.out.println("ACTIVITY LAUNCH");
                try {
                    Thread.sleep(3000);
                }
                catch (Exception e) {}
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return;
            }
            Intent intent = new Intent(MainActivity.this, MapView.class);
            startActivity(intent);
        }
        else if(cursor.getInt(0) == 1){
            //prompt login/signup
            //if alreayd logged in should skip


                String curruser = cursor.getString(2);
                String currpass = cursor.getString(1);
                mAuth.signInWithEmailAndPassword(curruser, currpass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    System.out.println("FLAG: "+ flag);
                                    if(flag == false) {
                                        flag = true;
                                        System.out.println("ACTIVITY LAUNCH");
                                        try {
                                            Thread.sleep(3000);
                                        }
                                        catch (Exception e) {}
                                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                                        return;
                                    }
                                    Intent intent = new Intent(MainActivity.this, MapView.class);
                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this, "Logged in.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }


            else {

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
        cursor.close();
        db.close();
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

    @Override
    public void onPause() {
        flag = false;
        super.onPause();
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
