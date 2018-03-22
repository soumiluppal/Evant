package com.cs307.evant.evant;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class eventPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teventpage);

        TextView title = findViewById(R.id.eventTitle);
        TextView descrip = findViewById(R.id.description);
        TextView Loc = findViewById(R.id.loc);
        TextView peps = findViewById(R.id.people);

        title.setText(getIntent().getStringExtra("Title"));
        descrip.setText(getIntent().getStringExtra("Description"));
        String location = "Location:  " + getIntent().getStringExtra("location") + "  " + getIntent().getStringExtra("dttime");
        Loc.setText(location);
        peps.setText(getIntent().getStringExtra("Host"));


    }

}
