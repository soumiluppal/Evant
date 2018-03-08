package com.cs307.evant.evant;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.sql.SQLOutput;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class CatActivity extends AppCompatActivity {

    HashMap<String, Boolean> checkButtons = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        checkButtons = (HashMap<String, Boolean>) getIntent().getSerializableExtra("map");



        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("map", checkButtons);
                setResult(Activity.RESULT_OK, intent);
                CatActivity.this.finish();
            }
        });
    }
}
