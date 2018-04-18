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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by avi12 on 2/22/2018.
 */

public class eventList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String category;
    private ArrayList<Integer> needIndexs = new ArrayList<>();
    private ArrayList<Integer> ndIndexs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cat_lst);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        category = getIntent().getStringExtra("Category");

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


        ArrayList<String> ttitles = new ArrayList<>();
        ArrayList<String> tdttime = new ArrayList<>();
        ArrayList<String> tloc = new ArrayList<>();
        ArrayList<String> tdescrip = new ArrayList<>();
        ArrayList<String> thst = new ArrayList<>();

        ArrayList<String[]> cats = new ArrayList<>();


        cats = db.getCategories();

        filterCats(cats);

        titles = db.getTitles();

        hst = db.getHost();

        dttime = db.getTime();

        loc = db.getLoc();

        descrip = db.getDescription();

        ntitles = stpdfilter(titles);
        ndttime = stpdfilter(dttime);
        nloc = stpdfilter(loc);
        ndescrip = stpdfilter(descrip);
        nhst = stpdfilter(hst);

        filterOld(ndttime);

        ttitles = stpdfilter2(titles);
        tdttime = stpdfilter2(dttime);
        tloc = stpdfilter2(loc);
        tdescrip = stpdfilter2(descrip);
        thst = stpdfilter2(hst);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy   HH:mm aa");
        String formattedDate = df.format(currentTime);
        formattedDate = formattedDate.toUpperCase();
        String toCompDate = dttime.get(1).substring(0,8);
        String reformatdate = formattedDate.substring(0,6) + formattedDate.substring(8);
        //System.out.println("Avi formattedDate = " + reformatdate + "times.get(index) = "+ toCompDate + "end");


        Collections.reverse(ntitles);
        Collections.reverse(ndttime);
        Collections.reverse(nloc);
        Collections.reverse(ndescrip);
        Collections.reverse(nhst);
        eventAdapter cadapter = new eventAdapter(ntitles,ndescrip,ndttime,nloc, nhst, this);


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

    private ArrayList<String> stpdfilter2(ArrayList<String> gve)
    {

        ArrayList<String> actualy = new ArrayList<>();

        for(int i = 0; i < ndIndexs.size(); i++)
        {
            actualy.add(gve.get(ndIndexs.get(i)));
        }
        return  actualy;
        //return gve;
    }

    private void filterOld(ArrayList<String> curr)
    {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy   HH:mm aa");
        String formattedDate = df.format(currentTime);
        formattedDate = formattedDate.toUpperCase();
        String toCompDate = "";
        String reformatdate = formattedDate.substring(0,6) + formattedDate.substring(8);
        for(int i = 0; i < curr.size(); i++)
        {
            toCompDate = curr.get(i).substring(0,8);
            int rty = reformatdate.compareTo(toCompDate);
            System.out.println("Avi formattedDate = " + reformatdate + "times.get(index) = "+ toCompDate + " result" + rty);
            if(reformatdate.compareTo(toCompDate) < 0)
            {
                ndIndexs.add(i);
            }
        }

    }




}