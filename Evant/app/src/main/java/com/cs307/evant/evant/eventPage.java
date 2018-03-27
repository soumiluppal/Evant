package com.cs307.evant.evant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static com.cs307.evant.evant.MainActivity.db;

public class eventPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teventpage);

        //this.setMovementMethod(new ScrollingMovementMethod());
        TextView title = findViewById(R.id.eventTitle);
        TextView descrip = findViewById(R.id.description);
        TextView Loc = findViewById(R.id.loc);
        TextView peps = findViewById(R.id.people);
        Button hostRating = (Button)findViewById(R.id.hostRating);


        title.setText(getIntent().getStringExtra("Title"));
        descrip.setText(getIntent().getStringExtra("Description"));
        String location = "Location:  " + getIntent().getStringExtra("location") + "  " + getIntent().getStringExtra("dttime");
        Loc.setText(location);
        System.out.println("HOST IN PAGE: " + getIntent().getStringExtra("Host"));
        peps.setText(getIntent().getStringExtra("Host"));
        final String tempHost = getIntent().getStringExtra("Host");

        hostRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "host rating clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(eventPage.this, host_rating.class);
                intent.putExtra("host", tempHost);
                startActivity(intent);
            }
        });

        final ImageView upButton = (ImageView) findViewById(R.id.upButton);
        final ImageView downButton = (ImageView) findViewById(R.id.downButton);
        upButton.setRotation(180);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    upButton.setClickable(false);
                    downButton.setClickable(true);
                    downButton.clearColorFilter();
                    upButton.setColorFilter(Color.argb(77, 77, 77, 77));
                    db.addThumbsUp(tempHost);
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //System.out.println("CHECK HERE::" +tempHost + "::");
                    upButton.setClickable(true);
                    downButton.setClickable(false);
                    upButton.clearColorFilter();
                    downButton.setColorFilter(Color.argb(77, 77, 77, 77));

                    db.addThumbsDown(tempHost);
            }
        });


    }

}
