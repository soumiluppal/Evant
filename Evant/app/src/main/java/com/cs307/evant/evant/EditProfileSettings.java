package com.cs307.evant.evant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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

import static com.cs307.evant.evant.MainActivity.db;

public class EditProfileSettings extends AppCompatActivity {
    Boolean pwdone = false;
    String oldpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //THIS IS NECESSARY:
        final EditText username = (EditText) findViewById(R.id.Password);
        final EditText password = (EditText) findViewById(R.id.Email);

        final EditText email = (EditText) findViewById(R.id.Username);
        final EditText interests = (EditText) findViewById(R.id.Interests);



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
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setMessage("Enter old password " )
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        oldpw =  input.getText().toString();
                        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpw);
                        System.out.println(oldpw);
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
}

