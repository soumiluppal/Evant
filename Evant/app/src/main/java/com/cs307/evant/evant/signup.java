package com.cs307.evant.evant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.mysql.jdbc.log.NullLogger;

/**
 * Created by avi12 on 2/16/2018.
 */

public class signup extends AppCompatActivity {

    /*test*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Button newButton = (Button) findViewById(R.id.register);
        EditText uname = (EditText) findViewById(R.id.username);
        String username = uname.getText().toString();
        EditText pass = (EditText) findViewById(R.id.password);
        String password = pass.getText().toString();
        EditText email = (EditText) findViewById(R.id.email);
        String eml = email.getText().toString();
        EditText conpass = (EditText) findViewById(R.id.confpassword);
        String repass = conpass.getText().toString();
        Switch klog = (Switch) findViewById(R.id.switch1);

        if(username.length() <= 0)
        {

        }
        if(password == "")
        {

        }
        if(eml == "")
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
                EditText email = (EditText) findViewById(R.id.email);
                String eml = email.getText().toString();
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

                if(eml.length() <= 0)
                {
                    builder.setMessage("Please enter your email")
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
                    try {
                        //User tmp = new User(username,password,eml);
                        DatabaseInterface db = new DatabaseInterface();
                        //db.addUser(tmp);
                    } catch (ConnectionNotEstablishedException e) {
                        e.printStackTrace();
                    }
                }





            }
        });


    }
}
