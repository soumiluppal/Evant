package com.cs307.evant.evant;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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
    private ArrayList<Integer> ndIndexs = new ArrayList<>();
    private ArrayList<Integer> nIndexs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cat_lst);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button button = findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(eventList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button profileButton = (Button) findViewById(R.id.profilebutton);



        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(eventList.this, Profile.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

        //attempt
        Button settingsButton = (Button) findViewById(R.id.settingsbutton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(eventList.this, Settings.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

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


        if(srching)
        {
            filterSearch(descrip);
            filterSearch(titles);
            filterSearch(loc);
            category = "none";

        }
        else if(category.equals("Suggested"))
        {
            ArrayList<String> interest = getInterst();
            for(int i = 0; i < interest.size(); i++)
            {
                filterCats2(cats,interest.get(i));
            }

        }
        else
        {
            filterCats(cats);
        }

        ntitles = stpdfilter(titles);
        ndttime = stpdfilter(dttime);
        nloc = stpdfilter(loc);
        ndescrip = stpdfilter(descrip);
        nhst = stpdfilter(hst);

        filterold(ndttime);

        ArrayList<String> ttitles = stpdfilter2(ntitles);
        ArrayList<String> tdttime = stpdfilter2(ndttime);
        ArrayList<String> tloc = stpdfilter2(nloc);
        ArrayList<String> thst = stpdfilter2(nhst);
        ArrayList<String> tdescrip = stpdfilter2(ndescrip);

        if(!srching && category.equals("Suggested"))
        {
            filterPopularity(ttitles);
            ttitles = stpdfilter3(ttitles);
            tdttime = stpdfilter3(tdttime);
            tloc = stpdfilter3(tloc);
            thst = stpdfilter3(thst);
            tdescrip= stpdfilter3(tdescrip);


        }


        eventAdapter cadapter = new eventAdapter(ttitles,tdescrip,tdttime,tloc, thst, lats, lngs, this);


        recyclerView.setAdapter(cadapter);

        EditText sbar = findViewById(R.id.searchBar);
        sbar.setVisibility(View.GONE);
    }


    private void filterCats(ArrayList<String[]> cts)
    {
        for(int i = 0; i < cts.size() ; i++)
        {
            if(Arrays.asList(cts.get(i)).contains(category))
            {
                if(!needIndexs.contains(i))
                    needIndexs.add(i);
                //System.out.println(i);
            }
        }

    }
    private void filterCats2(ArrayList<String[]> cts,String cats)
    {
        for(int i = 0; i < cts.size() ; i++)
        {
            if(Arrays.asList(cts.get(i)).contains(cats))
            {
                if(!needIndexs.contains(i))
                    needIndexs.add(i);
                //System.out.println(i);
            }
        }

    }



    private void filterSearch(ArrayList<String> cts)
    {
        System.out.println("Avi wrd: " + wrd);
        wrd = wrd.toLowerCase();
        for(int i = 0; i < cts.size() ; i++)
        {
            if(cts.get(i) != null && cts.get(i).toLowerCase().contains(wrd)) {
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

    private ArrayList<String> stpdfilter3(ArrayList<String> gve)
    {

        ArrayList<String> actualy = new ArrayList<>();

        for(int i = 0; i < nIndexs.size(); i++)
        {
            actualy.add(gve.get(nIndexs.get(i)));
        }
        return  actualy;
        //return gve;
    }

    private void filterold(ArrayList<String> cts)
    {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE,-1);
        Date currentTime = ca.getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy   HH:mm aa");
        String formattedDate = df.format(currentTime);
        formattedDate = formattedDate.toUpperCase();
        for(int i = 0; i < cts.size(); i++)
        {
            if (formattedDate.compareTo(cts.get(i)) <= 0)
            {
                ndIndexs.add(i);

            }
        }

    }

    public ArrayList<String> getInterst()
    {
        ArrayList<String> ans = new ArrayList<>();
        SQLiteOpenHelper DatabaseHelper = new DataHelp(eventList.this);
        SQLiteDatabase dbs = DatabaseHelper.getReadableDatabase();
        Cursor cursor = dbs.query("LOGINDATA", new String[]{"INTRST"}, null, null, null, null, "_id DESC");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            ans.add(cursor.getString(0));
            System.out.println("tmp cursor: " + cursor.getString(0));
        }

        cursor.close();

        //dbs.close();
        return ans;
    }
    private void filterPopularity(ArrayList<String> nms)
    {
        final ArrayList<ArrayList<String>> attendeeList = db.getAttendees();
        final ArrayList<String> eventTitles = db.getTitles();
        for(int i = 0; i < nms.size(); i++)
        {
            int eventIndex = eventTitles.indexOf(nms.get(i));
            //final ArrayList<String> list = db.getAttendees().get(eventIndex);
            if(attendeeList.get(eventIndex).size() > 3)
                nIndexs.add(i);

        }

    }




}