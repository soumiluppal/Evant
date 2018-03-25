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
        CheckBox check = (CheckBox) findViewById(R.id.sportsCheck);
        check.setChecked(checkButtons.get("Sports"));
        check = (CheckBox) findViewById(R.id.foodCheck);
        check.setChecked(checkButtons.get("Food"));
        check = (CheckBox) findViewById(R.id.gamingCheck);
        check.setChecked(checkButtons.get("Video Games"));
        check = (CheckBox) findViewById(R.id.eduCheck);
        check.setChecked(checkButtons.get("Education"));
        check = (CheckBox) findViewById(R.id.musicCheck);
        check.setChecked(checkButtons.get("Music"));
        check = (CheckBox) findViewById(R.id.moviecheck);
        check.setChecked(checkButtons.get("Movies"));
        check = (CheckBox) findViewById(R.id.workoutCheck);
        check.setChecked(checkButtons.get("Workout"));
        check = (CheckBox) findViewById(R.id.hobbiesCheck);
        check.setChecked(checkButtons.get("Hobbies"));


        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title;
                CheckBox box = (CheckBox)findViewById(R.id.sportsCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                }
                box = (CheckBox)findViewById(R.id.foodCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                }
                box = (CheckBox)findViewById(R.id.gamingCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                }
                box = (CheckBox)findViewById(R.id.eduCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                }
                box = (CheckBox)findViewById(R.id.musicCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                }
                box = (CheckBox)findViewById(R.id.moviecheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                }
                box = (CheckBox)findViewById(R.id.workoutCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                }
                box = (CheckBox)findViewById(R.id.hobbiesCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                }
                Intent intent = new Intent();
                intent.putExtra("map", checkButtons);
                setResult(Activity.RESULT_OK, intent);
                CatActivity.this.finish();
            }
        });
    }
}
