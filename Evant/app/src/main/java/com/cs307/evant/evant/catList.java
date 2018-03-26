package com.cs307.evant.evant;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by avi12 on 2/22/2018.
 */

public class catList extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cat_lst);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button button = findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(catList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button listButton = (Button) findViewById(R.id.listbutton);

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(catList.this, catList.class);
                startActivity(intent);
            }
        });

        Button profileButton = (Button) findViewById(R.id.profilebutton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(catList.this, Profile.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

        //attempt
        Button settingsButton = (Button) findViewById(R.id.settingsbutton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(catList.this, Settings.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.categories);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String>ctnms = new ArrayList<>();
        ctnms.add("Sports");
        ctnms.add("Social");
        ctnms.add("Education");
        ctnms.add("Video Games");
        ctnms.add("Community");
        ctnms.add("Food");
        ctnms.add("Flash Mobs");
        ctnms.add("Sales");
        catAdapter cadapter = new catAdapter(ctnms,this);
        recyclerView.setAdapter(cadapter);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
