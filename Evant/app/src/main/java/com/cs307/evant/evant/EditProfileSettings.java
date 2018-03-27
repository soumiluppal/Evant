package com.cs307.evant.evant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.cs307.evant.evant.MainActivity.db;

public class EditProfileSettings extends AppCompatActivity {

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
                if(!username.getText().toString().equals("")){
                    System.out.println("empty: " + username.getText().toString());
                    db.updateName(db.getUid(), username.getText().toString());
                }
                Intent intent = new Intent(EditProfileSettings.this, Settings.class);
                startActivity(intent);
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

}
