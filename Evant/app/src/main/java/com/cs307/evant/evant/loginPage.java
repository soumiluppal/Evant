package com.cs307.evant.evant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by avi12 on 2/16/2018.
 */

public class loginPage extends AppCompatActivity {

    /*test*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        Switch slog = findViewById(R.id.stayLog);

        Button logn = (Button) findViewById(R.id.login);
        final EditText uname = (EditText) findViewById(R.id.userName);
        final EditText pass = findViewById(R.id.password);
        pass.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        SQLiteOpenHelper DatabaseHelper = new DataHelp(loginPage.this);
        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query("LOGINDATA", new String[]{"LOGGED", "PASS","USER"}, null, null, null, null, "_id DESC");
        cursor.moveToFirst();

        if(cursor.getInt(0) == 1)
        {
            String curruser = cursor.getString(2);
            String currpass = cursor.getString(1);
            uname.setText(curruser);
            pass.setText(currpass);


            mAuth.signInWithEmailAndPassword(curruser, currpass)
                    .addOnCompleteListener(loginPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(loginPage.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(loginPage.this, "Logged in.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(loginPage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

        }
        cursor.close();
        db.close();
        slog.setChecked(true);


        logn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(loginPage.this);
                String user = uname.getText().toString();
                String paswrd = pass.getText().toString();
                Boolean nvalid = false;
                Switch slog = findViewById(R.id.stayLog);


                if(paswrd.length() <= 0)
                {
                    builder.setMessage("Please enter a Password")
                            .setTitle("Incomplete Page");
                    //AlertDialog dialog = builder.create();
                    nvalid = true;
                    //dialog.show();
                }

                if(user.length() <= 0)
                {
                    builder.setMessage("Please enter a User Name or Email")
                            .setTitle("Incomplete Page");
                    //AlertDialog dialog = builder.create();
                    nvalid = true;
                    //dialog.show();
                }

                if(nvalid)
                {
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    nvalid = false;
                }
                else {

                    if(slog.isChecked())
                    {
                        SQLiteOpenHelper DatabaseHelper = new DataHelp(loginPage.this);
                        SQLiteDatabase db = DatabaseHelper.getReadableDatabase();
                        //cursor = db.query("LOGINDATA", new String[]{"CAT", "PATH","CNT","ROTATE","IMAGE"}, null, null, null, null, "_id DESC");

                        db.delete("LOGINDATA", null, null);

                        ContentValues cv = new ContentValues();

                        cv.put("USER",user);
                        cv.put("PASS", paswrd);
                        cv.put("LOGGED",1);
                        db.insert("LOGINDATA",null,cv);

                    }
                    mAuth.signInWithEmailAndPassword(user, paswrd)
                            .addOnCompleteListener(loginPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(loginPage.this, MapView.class);
                                        startActivity(intent);
                                        Toast.makeText(loginPage.this, "Logged in.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(loginPage.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }


            }
        });

    }
}
