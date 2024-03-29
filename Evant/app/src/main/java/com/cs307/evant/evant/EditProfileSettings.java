package com.cs307.evant.evant;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import static com.cs307.evant.evant.MainActivity.db;

public class EditProfileSettings extends AppCompatActivity {
    Boolean pwdone = false;
    String oldpw;
    String intrst;
    EditText interests;
    HashMap<String, Boolean> checkButtons = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkButtons.put("Sports", false);
        checkButtons.put("Social", false);
        checkButtons.put("Education", false);
        checkButtons.put("Gaming", false);
        checkButtons.put("Food", false);
        checkButtons.put("Community", false);
        checkButtons.put("Music", false);
        checkButtons.put("Art", false);
        checkButtons.put("Others", false);

        //THIS IS NECESSARY:
        final EditText username = (EditText) findViewById(R.id.Password);
        final EditText password = (EditText) findViewById(R.id.Email);
        password.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        

        final EditText email = (EditText) findViewById(R.id.Username);
        interests = (EditText) findViewById(R.id.Interests);


        interests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditProfileSettings.this, CatActivity.class);
                //startActivity(intent);
                intent.putExtra("map", checkButtons);
                startActivityForResult(intent, 102);
            }
        });
        //Apply button
        Button ApplyButton = (Button) findViewById(R.id.Apply);

        ApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("password: " + password.getText().toString());
                if(!password.getText().toString().equals("")){
                    pwdone = true;
                    System.out.println("not empty");
                    changePass(password.getText().toString(), true);
                }
                if(!username.getText().toString().equals("")){
                    db.updateName(db.getUid(), username.getText().toString());
                }
                if(!pwdone){
                    Intent intent = new Intent(EditProfileSettings.this, Settings.class);
                    startActivity(intent);
                }

                if(!interests.getText().toString().equals("")){
                    String tmp = "";
                    tmp = "";
                    System.out.println("tmp full = " + intrst);
                    ArrayList<String> myInterests = new ArrayList<>();
                    for(int i = 0; i < intrst.length();i++)
                    {
                        if(intrst.charAt(i) == ' ')
                        {
                            System.out.println("tmp = " + tmp);
                            if(interstNContains(tmp))
                            {
                                System.out.println("tmp added = " + tmp);
                                if(tmp != "") {
                                    myInterests.add(tmp);
                                }
                            }
                            tmp = "";
                        }
                        else {
                            tmp = tmp + intrst.charAt(i);
                        }
                    }
                    db.updateInterest(db.getUid(), myInterests);
                }
            }
        });


        // Cancel button
        Button CancelButton = (Button) findViewById(R.id.Cancel);

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileSettings.this, Settings.class);
                startActivity(intent);
            }
        });


    }

    void changePass(String pw, final boolean last){
        final String newPass = pw;
        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditProfileSettings.this);
        alertDialogBuilder.setTitle("Enter old password: ");
        final EditText input = new EditText(EditProfileSettings.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setMessage("Enter old password " )
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        oldpw =  input.getText().toString();
                        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpw);
                        System.out.println(oldpw);
                        //local database pass change
                        SQLiteOpenHelper DatabaseHelper = new DataHelp(EditProfileSettings.this);
                        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();
                        //cursor = db.query("LOGINDATA", new String[]{"CAT", "PATH","CNT","ROTATE","IMAGE"}, null, null, null, null, "_id DESC");

                        //db.delete("LOGINDATA", null, null);

                        ContentValues cv = new ContentValues();

                        cv.put("PASS", newPass);
                        cv.put("LOGGED",1);
                        db.insert("LOGINDATA",null,cv);

                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(EditProfileSettings.this, "Password Updated.",
                                                                Toast.LENGTH_SHORT).show();
                                                        //Todo: update password in local database
                                                        if(last) {
                                                            Intent intent = new Intent(EditProfileSettings.this, Settings.class);
                                                            startActivity(intent);
                                                        }
                                                    } else {
                                                        Log.d("d", "Error password not updated");
                                                        Toast.makeText(EditProfileSettings.this, "Please enter a valid password.",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(EditProfileSettings.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intrst = data.getStringExtra("checked");
        interests.setText(intrst);

    }

    public boolean interstNContains(String str)
    {
        SQLiteOpenHelper DatabaseHelper = new DataHelp(EditProfileSettings.this);
        SQLiteDatabase dbs = DatabaseHelper.getReadableDatabase();
        Cursor cursor = dbs.query("LOGINDATA", new String[]{"INTRST"}, null, null, null, null, "_id DESC");
        //cursor.moveToFirst();
        String tmp;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            if(cursor.getString(0) == null)
                cursor.moveToNext();
            if(cursor.getString(0) == null)
                break;
            if(cursor.getString(0).equals(str))
            {
                return false;
            }
        }
        cursor.close();
        dbs.close();
        return true;
    }
}

