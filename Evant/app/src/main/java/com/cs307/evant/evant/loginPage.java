package com.cs307.evant.evant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by avi12 on 2/16/2018.
 */

public class loginPage extends AppCompatActivity {

    /*test*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        Button logn = (Button) findViewById(R.id.login);
        final EditText uname = (EditText) findViewById(R.id.userName);
        final EditText pass = findViewById(R.id.password);

        logn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(loginPage.this);
                String user = uname.getText().toString();
                String paswrd = pass.getText().toString();
                Boolean nvalid = false;


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
                mAuth.signInWithEmailAndPassword(user,paswrd)
                        .addOnCompleteListener(loginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    System.out.println("logged in");
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(loginPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });


            }
        });

    }
}
