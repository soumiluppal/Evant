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
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by avi12 on 2/16/2018.
 */

public class signup extends AppCompatActivity {

    /*test*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String uid = db.getUid();


        Button newButton = (Button) findViewById(R.id.register);
        EditText uname = (EditText) findViewById(R.id.username);
        String username = uname.getText().toString();
        EditText pass = (EditText) findViewById(R.id.password);
        String password = pass.getText().toString();
        EditText fullname = (EditText) findViewById(R.id.fullname);
        String nm = fullname.getText().toString();
        EditText conpass = (EditText) findViewById(R.id.confpassword);
        String repass = conpass.getText().toString();
        Switch klog = (Switch) findViewById(R.id.switch1);

        if(username.length() <= 0)
        {

        }
        if(password == "")
        {

        }
        if(nm == "")
        {

        }
        if(repass == "")
        {

        }



        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(signup.this);
                EditText uname = (EditText) findViewById(R.id.username);
                String username = uname.getText().toString();
                EditText pass = (EditText) findViewById(R.id.password);
                String password = pass.getText().toString();
                EditText fullname = (EditText) findViewById(R.id.fullname);
                String nm = fullname.getText().toString();
                EditText conpass = (EditText) findViewById(R.id.confpassword);
                String repass = conpass.getText().toString();
                Switch klog = (Switch) findViewById(R.id.switch1);
                boolean nvalid = false;


                System.out.println("pass: %l and repass: " + password + " "+ repass);

                Log.d("Passchck","password: " + password + "repass: " +repass );
                if(!password.equals(repass))
                {
                    builder.setMessage("Passwords Do not Match")
                            .setTitle("Incomplete Page");
                    //AlertDialog dialog = builder.create();
                    nvalid = true;
                    //dialog.show();
                }

                if(repass.length() <= 0)
                {
                    builder.setMessage("Please confirm your password")
                            .setTitle("Incomplete Page");
                    //AlertDialog dialog = builder.create();
                    nvalid = true;
                    //dialog.show();
                }



                if(password.length() <= 0)
                {
                    builder.setMessage("Please Fill In a Password")
                            .setTitle("Incomplete Page");
                    //AlertDialog dialog = builder.create();
                    nvalid = true;
                    //dialog.show();
                }

                if(nm.length() <= 0)
                {
                    builder.setMessage("Please enter your name")
                            .setTitle("Incomplete Page");
                    //AlertDialog dialog = builder.create();
                    nvalid = true;
                    //dialog.show();

                }

                if(username.length() <= 0)
                {
                    builder.setMessage("Please Fill In a User Name")
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
                else
                {
                    final String nam = nm;
                    mAuth.createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        db.updateName(uid, nam);

                                        Intent intent = new Intent(signup.this, MapView.class);
                                        startActivity(intent);
                                        Toast.makeText(signup.this, "Signup success.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        try{
                                            throw task.getException();
                                        }catch(Exception e){
                                            System.out.println(e);
                                        }
                                        Toast.makeText(signup.this, "Authentication failed.",
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
