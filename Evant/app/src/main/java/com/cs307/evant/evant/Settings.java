package com.cs307.evant.evant;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static com.cs307.evant.evant.MainActivity.db;

public class Settings extends AppCompatActivity {
    private static final String EMAIL = "email";

    // Global variables
    final Context context = this;
    Button setRadius;
    private double radiusVal = db.getRadius(db.getUid());
    private String radiusStr = "";
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // back button
        Button backButton = (Button) findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });



        Button profileButton = (Button) findViewById(R.id.profilebutton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Profile.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

        //attempt
        Button settingsButton = (Button) findViewById(R.id.settingsbutton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, Settings.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });
        //attempt end

        Button EditProfileButton = (Button) findViewById(R.id.EditProfileButton);

        EditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, EditProfileSettings.class);
                startActivity(intent);
            }
        });


        //set Location
        Button setLocation = (Button) findViewById(R.id.SetLocation);
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, LocationSelectView.class);
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


        // set radius button
        setRadius = (Button) findViewById(R.id.SetRadiusButton);
        setRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radiusVal = db.getRadius(db.getUid());
                //ShowDialog();
                Toast.makeText(getApplicationContext(), "DISTANCE = " + radiusVal, Toast.LENGTH_SHORT).show();

                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Set Radius");
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alertDialogBuilder.setView(input);

                alertDialogBuilder.setMessage("Current radius is = " + radiusVal)
                        .setCancelable(false)
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                radiusStr = input.getText().toString();
                                radiusVal = Double.parseDouble(radiusStr);
                                db.updateRadius(db.getUid(),radiusVal);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        Button historyButton = (Button) findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, atten_events.class);
                startActivity(intent);
            }
        });
        final File file = getFileStreamPath("notification_settings.txt");
        Switch notif = (Switch) findViewById(R.id.notifiSwitch);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String state = br.readLine();
            br.close();
            System.out.println("CHECK ME: " + state);
            if(state.equals("ON")) {
                notif.setChecked(true);
            }
            else {
                notif.setChecked(false);
            }
        }
        catch (Exception e){

        }
        notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                        bw.write("ON");
                        bw.flush();
                        bw.close();
                    }
                    catch (Exception e){
                        System.out.println("PROBLEM: " + e);
                    }
                }
                else {
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                        bw.write("OFF");
                        bw.flush();
                        bw.close();
                    }
                    catch (Exception e){
                        System.out.println("PROBLEM: " + e);
                    }
                }
            }
        });



        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        System.out.println("PROFILE MAN: " + loginResult.getAccessToken().getUserId());
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }


    public void ShowDialog()
    {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final SeekBar seek = new SeekBar(this);
        seek.setMax(10000);
        // not sure about this one
        seek.setProgress(db.getRating(db.getUid()));
        popDialog.setIcon(android.R.drawable.ic_menu_compass);
        popDialog.setTitle("Set Radius");
        popDialog.setView(seek);

        // Button OK
        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        //seek Change Listener
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Do something here with new value
                // not sure about his one
                db.updateRadius(db.getUid(), progress);
            }
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        popDialog.create();
        popDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
