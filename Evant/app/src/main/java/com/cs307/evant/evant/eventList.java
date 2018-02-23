package com.cs307.evant.evant;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import java.util.ArrayList;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by avi12 on 2/22/2018.
 */

public class eventList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cat_lst);
        recyclerView = (RecyclerView) findViewById(R.id.categories);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(eventList.this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(eventList.this);
        //recyclerView.addItemDecoration(new DividerItemDecoration(HomeScreen.this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //populate arrays
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> dttime = new ArrayList<>();
        ArrayList<String> loc = new ArrayList<>();
        ArrayList<String> descrip = new ArrayList<>();

        titles = db.getTitles();
        /*
        titles.add("MIT Hackathon");
        titles.add("Differential Eqs Study session MA261");
        titles.add("Pick Up Soccer Game");
        titles.add("Potluck Dinner");
        titles.add("Party Until We Pass Out");
        titles.add("Painting Session");*/

        dttime = db.getTime();
        /*
        dttime.add("02.29.2018   12:30 am - 11:30 pm");
        dttime.add("03.01.2018   05:30 pm - 11:59 pm");
        dttime.add("03.01.2018   02:30 pm - 06:30 pm");
        dttime.add("03.05.2018   07:30 pm - 09:30 pm");
        dttime.add("03.06.2018   10:30 pm - 04:30 am");
        dttime.add("03.09.2018   12:30 pm - 05:30 pm");*/

        loc = db.getLoc();
        /*
        loc.add("MIT Purdue Hall");
        loc.add("Purdue Math Help Room 1");
        loc.add("Purdue Co Rec");
        loc.add("1120 Northwestern ave, West Lafayette");
        loc.add("DELTA DELTA DELTA Sorority");
        loc.add("Purdue Engineering Fountain");*/

        descrip = db.getDescription();
        /*
        descrip.add("Join for a Hackathon at MIT!");
        descrip.add("Exam 1 coming up, come study!");
        descrip.add("Soccer for everyone!");
        descrip.add("Cook your favorite meals and come share and enjoy with others");
        descrip.add("Party before exam season!!!");
        descrip.add("Come paint landscapes and be inspired by others");
        */


        //
        eventAdapter cadapter = new eventAdapter(titles,descrip,dttime,loc);
        recyclerView.setAdapter(cadapter);
    }

}