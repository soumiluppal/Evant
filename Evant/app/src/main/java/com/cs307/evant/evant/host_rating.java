package com.cs307.evant.evant;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by Soun Kim on 2018-03-25.
 */

public class host_rating extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_rating);

        TextView tvHost = findViewById(R.id.eventHost);
        TextView tvRating = findViewById(R.id.ratingVal);

        String host = getIntent().getStringExtra("host");
        tvHost.setText(host);
        int rating;
/*
        if(db.getRating(host) == 0){
            rating = 0;
        }
        else{
            rating = db.getRating(host);
        }

        tvRating.setText(rating);
*/
    }

}
