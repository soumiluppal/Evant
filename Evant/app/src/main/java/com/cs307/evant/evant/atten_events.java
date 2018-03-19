package com.cs307.evant.evant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by Soun Kim on 2018-03-16.
 */

public class atten_events extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String category;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cat_lst);
        recyclerView = (RecyclerView) findViewById(R.id.categories);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(atten_events.this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(atten_events.this);
        //recyclerView.addItemDecoration(new DividerItemDecoration(HomeScreen.this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        dummyList();
    }



    private void dummyList(){
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> descrips = new ArrayList<String>();
        ArrayList<String> loc = new ArrayList<String>();
        ArrayList<String> dtTime = new ArrayList<String>();

        titles.add("dummy1");
        descrips.add("dummy1 descrips");
        loc.add("dummy1 loc");
        dtTime.add("dummy1 dtTime");

        titles.add("dummy2");
        descrips.add("dummy2 descrips");
        loc.add("dummy2 loc");
        dtTime.add("dummy2 dtTime");

        titles.add("dummy3");
        descrips.add("dummy3 descrips");
        loc.add("dummy3 loc");
        dtTime.add("dummy3 dtTime");

        eventAdapter eAdapter = new eventAdapter(titles,descrips, loc, dtTime, this);
        recyclerView.setAdapter(eAdapter);
    }

}
