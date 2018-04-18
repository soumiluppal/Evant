package com.cs307.evant.evant;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.WindowManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by avi12 on 2/22/2018.
 */

public class eventList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String category;
    private String wrd = "";
    boolean srching;
    private ArrayList<Integer> needIndexs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cat_lst);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if(getIntent().getStringExtra("Searching") != null && getIntent().getStringExtra("Searching").equals("true"))
        {
            wrd = getIntent().getStringExtra("Word");
            srching = true;
        }
        else {
            category = getIntent().getStringExtra("Category");
            srching = false;
        }

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
        ArrayList<String> hst = new ArrayList<>();

        ArrayList<String> ntitles = new ArrayList<>();
        ArrayList<String> ndttime = new ArrayList<>();
        ArrayList<String> nloc = new ArrayList<>();
        ArrayList<String> ndescrip = new ArrayList<>();
        ArrayList<String> nhst = new ArrayList<>();
        ArrayList<Double> lats = new ArrayList<>();
        ArrayList<Double> lngs = new ArrayList<>();

        ArrayList<String[]> cats = new ArrayList<>();


        cats = db.getCategories();


        titles = db.getTitles();

        hst = db.getHost();

        dttime = db.getTime();

        loc = db.getLoc();

        descrip = db.getDescription();

        if(!srching)
        {
            filterCats(cats);

        }
        else
        {
            filterSearch(descrip);
            filterSearch(titles);
            filterSearch(loc);
        }

        ntitles = stpdfilter(titles);
        ndttime = stpdfilter(dttime);
        nloc = stpdfilter(loc);
        ndescrip = stpdfilter(descrip);
        nhst = stpdfilter(hst);


        eventAdapter cadapter = new eventAdapter(ntitles,ndescrip,ndttime,nloc, nhst, lats, lngs, this);


        recyclerView.setAdapter(cadapter);
    }


    private void filterCats(ArrayList<String[]> cts)
    {
        for(int i = 0; i < cts.size() ; i++)
        {
            if(Arrays.asList(cts.get(i)).contains(category))
            {
                needIndexs.add(i);
                //System.out.println(i);
            }
        }

    }

    private void filterSearch(ArrayList<String> cts)
    {
        System.out.println("Avi wrd: " + wrd);
        for(int i = 0; i < cts.size() ; i++)
        {
            if(cts.get(i) != null && cts.get(i).contains(wrd)) {
                if (!needIndexs.contains(i))
                    needIndexs.add(i);
                //System.out.println(i);
            }
        }

    }

    private ArrayList<String> stpdfilter(ArrayList<String> gve)
    {

       ArrayList<String> actualy = new ArrayList<>();

        for(int i = 0; i < needIndexs.size(); i++)
        {
            actualy.add(gve.get(needIndexs.get(i)));
        }
        return  actualy;
       //return gve;
    }




}