package com.cs307.evant.evant;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class CatActivity extends AppCompatActivity {

    HashMap<String, Boolean> checkButtons = new HashMap<>();
    ArrayList<String>cked = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        checkButtons = (HashMap<String, Boolean>) getIntent().getSerializableExtra("map");
        CheckBox check = (CheckBox) findViewById(R.id.sportsCheck);
        check.setChecked(checkButtons.get("Sports"));
        check = (CheckBox) findViewById(R.id.socialCheck);
        check.setChecked(checkButtons.get("Social"));
        check = (CheckBox) findViewById(R.id.educationCheck);
        check.setChecked(checkButtons.get("Education"));
        check = (CheckBox) findViewById(R.id.gamingCheck);
        check.setChecked(checkButtons.get("Gaming"));
        check = (CheckBox) findViewById(R.id.foodCheck);
        check.setChecked(checkButtons.get("Food"));
        check = (CheckBox) findViewById(R.id.communitycheck);
        check.setChecked(checkButtons.get("Community"));
        check = (CheckBox) findViewById(R.id.musicCheck);
        check.setChecked(checkButtons.get("Music"));
        check = (CheckBox) findViewById(R.id.artCheck);
        check.setChecked(checkButtons.get("Art"));
        check = (CheckBox) findViewById(R.id.othersCheck);
        check.setChecked(checkButtons.get("Others"));


        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title;
                CheckBox box = (CheckBox)findViewById(R.id.sportsCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                    cked.add(title);
                }
                box = (CheckBox)findViewById(R.id.foodCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                    cked.add(title);
                }
                box = (CheckBox)findViewById(R.id.gamingCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                    cked.add(title);
                }
                box = (CheckBox)findViewById(R.id.educationCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                    cked.add(title);
                }
                box = (CheckBox)findViewById(R.id.musicCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                    cked.add(title);
                }
                box = (CheckBox)findViewById(R.id.artCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                    cked.add(title);
                }
                box = (CheckBox)findViewById(R.id.socialCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                    cked.add(title);
                }
                box = (CheckBox)findViewById(R.id.communitycheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                    cked.add(title);
                }
                box = (CheckBox)findViewById(R.id.othersCheck);
                if(box.isChecked()) {
                    title = box.getText().toString();
                    checkButtons.put(title, true);
                    cked.add(title);
                }
                Intent intent = new Intent();
                String str = "";
                for(int i = 0; i < cked.size();i++)
                {
                    str += " " + cked.get(i);
                }
                intent.putExtra("map", checkButtons);
                intent.putExtra("checked",str);
                setResult(Activity.RESULT_OK, intent);
                CatActivity.this.finish();
            }
        });
    }
}
