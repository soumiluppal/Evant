package com.cs307.evant.evant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by avi12 on 2/16/2018.
 */

public class loginPage extends AppCompatActivity {

    /*test*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

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

            }
        });

    }
}
