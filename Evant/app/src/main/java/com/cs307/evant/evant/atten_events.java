package com.cs307.evant.evant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by Soun Kim on 2018-03-16.
 */

public class atten_events extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String category;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atten_events);
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
   //     dummyList();
        loadMyEvents();

        final ToggleButton distButton = (ToggleButton) findViewById(R.id.distButton);
        final ToggleButton dateButton = (ToggleButton) findViewById(R.id.dateButton);
        final ToggleButton titleButton = (ToggleButton) findViewById(R.id.titleButton);
        titleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    dateButton.setChecked(false);
                    distButton.setChecked(false);
                    compoundButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                else {
                    compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    compoundButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });


        dateButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    titleButton.setChecked(false);
                    distButton.setChecked(false);
                    compoundButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    // Adam's sorting function call
                    loadMyEvents(2);
                }
                else {
                    compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    compoundButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                    loadMyEvents(0);
                }
            }
        });


        distButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    titleButton.setChecked(false);
                    dateButton.setChecked(false);
                    compoundButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                else {
                    compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    compoundButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });

    }


    private void loadMyEvents(){
        ArrayList<String> myEvents = db.getMyEvents(db.getUid());
        System.out.println(":::::" + myEvents.size());
        ArrayList<String> myDescrips = new ArrayList<>();
        ArrayList<String> myLoc = new ArrayList<>();
        ArrayList<String> myTime = new ArrayList<>();
        ArrayList<String> myHst = new ArrayList<>();

        ArrayList<String> myAttended = new ArrayList<>();
        ArrayList<String> titles = db.getTitles();
        ArrayList<String> descrips = db.getDescription();
        ArrayList<String> loc = db.getLoc();
        ArrayList<String> dtTime = db.getTime();
        ArrayList<String> hst = db.getHost();

        for(int a=0; a<myEvents.size(); a++){
            for(int b=0; b<titles.size(); b++){
                if (myEvents.get(a) == titles.get(b)) {
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy   HH:mm aa");
                    String formattedDate = df.format(currentTime);
                    formattedDate = formattedDate.toUpperCase();
                    if (formattedDate.compareTo(dtTime.get(b)) > 0) {
                        myAttended.add(titles.get(b));
                        myDescrips.add(descrips.get(b));
                        myLoc.add(loc.get(b));
                        myTime.add(dtTime.get(b));
                        myHst.add(hst.get(b));
                    }
                }
            }
        }
        /*
        for(int a=0; a<myEvents.size(); a++){
            for(int b=0; b<titles.size(); b++){
                System.out.println(myEvents.indexOf(a) + " ::::::::::::: " + titles.indexOf(b));
                if(myEvents.get(a) == titles.get(b)){
                    myDescrips.add(descrips.get(b));
                    myLoc.add(loc.get(b));
                    myTime.add(dtTime.get(b));
                    myHst.add(hst.get(b));
                }
            }
        }
        */

        eventAdapter eAdapter = new eventAdapter(myAttended,myDescrips, myLoc, myTime, myHst, this);
        recyclerView.setAdapter(eAdapter);
    }

    // overloaded load my events method with int for sorting.
    // This method can be called in the button onclick method to reload the page with se sourted list
    // The sortby int tells what to sort by.
    // Explination below
    // 0 = title ascending
    // 1 = title descending
    // 2 = date ascending
    // 3 = date descending
    // 4 = distance ascending
    // 5 = distance decending
    private void loadMyEvents(int sortby){
        ArrayList<String> myEvents = db.getMyEvents(db.getUid());
        System.out.println(":::::" + myEvents.size());
        ArrayList<String> myDescrips = new ArrayList<>();
        ArrayList<String> myLoc = new ArrayList<>();
        ArrayList<String> myTime = new ArrayList<>();
        ArrayList<String> myHst = new ArrayList<>();
        ArrayList<String> myAttended = new ArrayList<>();

        ArrayList<String> titles = db.getTitles();
        ArrayList<String> descrips = db.getDescription();
        ArrayList<String> loc = db.getLoc();
        ArrayList<String> dtTime = db.getTime();
        ArrayList<String> hst = db.getHost();

        for(int a=0; a<myEvents.size(); a++){
            for(int b=0; b<titles.size(); b++){
                if (myEvents.get(a) == titles.get(b)) {
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy   HH:mm aa");
                    String formattedDate = df.format(currentTime);
                    formattedDate = formattedDate.toUpperCase();
                    if (formattedDate.compareTo(dtTime.get(b)) > 0) {
                        myAttended.add(titles.get(b));
                        myDescrips.add(descrips.get(b));
                        myLoc.add(loc.get(b));
                        myTime.add(dtTime.get(b));
                        myHst.add(hst.get(b));
                    }
                }
            }
        }

        bubbleSort(myDescrips, myLoc, myTime, myHst, myAttended, sortby);

        eventAdapter eAdapter = new eventAdapter(myAttended,myDescrips, myLoc, myTime, myHst, this);
        recyclerView.setAdapter(eAdapter);
    }


    // bubble sort function to sort all of the array lists needed before desplaying everything
    // sortby int tells what to sort by
    // 0 = title ascending
    // 1 = title descending
    // 2 = date ascending
    // 3 = date descending
    // 4 = distance ascending
    // 5 = distance decending
    static void bubbleSort(ArrayList<String> myDescrips, ArrayList<String> myLoc, ArrayList<String> myTime, ArrayList<String> myHst, ArrayList<String> myAttended, int sortby)
    {
        int n = myDescrips.size();
        int i, j;
        boolean swapped;
        for (i = 0; i < n - 1; i++)
        {
            swapped = false;
            for (j = 0; j < n - i - 1; j++)
            {
                if (sortby == 0){
                    // TODO swap if title by title less
                }
                if (sortby == 1){
                    // TODO swap if title by title greater
                }
                if (sortby == 2){
                    System.out.println(myTime.get(j) + ":" + parsedate(myTime.get(j)));
                    System.out.println(myTime.get(j +1) + ":" + parsedate(myTime.get(j + 1)));
                    if (parsedate(myTime.get(j)) < parsedate(myTime.get(j + 1)))
                    {
                        // swap j and j + 1 for all arrays
                        Collections.swap(myDescrips, j, j+1);
                        Collections.swap(myLoc, j, j+1);
                        Collections.swap(myTime, j, j+1);
                        Collections.swap(myHst, j, j+1);
                        Collections.swap(myAttended, j, j+1);
                    }
                }
                if (sortby == 3){
                    System.out.println(myTime.get(j) + ":" + parsedate(myTime.get(j)));
                    System.out.println(myTime.get(j +1) + ":" + parsedate(myTime.get(j + 1)));
                    // Integer.parseInt(myTime.get(j)) < Integer.parseInt(myTime.get(j + 1))
                    if (parsedate(myTime.get(j)) < parsedate(myTime.get(j + 1)))
                    {
                        // swap j and j + 1 for all arrays
                        Collections.swap(myDescrips, j, j+1);
                        Collections.swap(myLoc, j, j+1);
                        Collections.swap(myTime, j, j+1);
                        Collections.swap(myHst, j, j+1);
                        Collections.swap(myAttended, j, j+1);
                    }
                }
                if (sortby == 4){
                    //TODO swap if distance less
                }
                if (sortby == 5){
                    //TODO swap if distance greater
                }


            }
            // IF no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }


    //04/04/2018   14:40 PM
    //03/25/18   03:09 PM
    // takes in a date as a string and produces a number value of the date
    static private int parsedate(String date){
        // parse date
        if (date.matches("/(0[1-9]|1[012])[- \\/.](0[1-9]|[12][0-9]|3[01])[- \\/.](19|20)\\d\\d/")) {
            return Character.getNumericValue(date.charAt(0))*10000000 + Character.getNumericValue(date.charAt(1)) + 1000000;
        }
        // temp
        if (Character.isDigit(date.charAt(1))){
            return Character.getNumericValue(date.charAt(1));
        }

        return 0;
        // TODO (Adam) improve this
    }


    private void dummyList(){
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> descrips = new ArrayList<String>();
        ArrayList<String> loc = new ArrayList<String>();
        ArrayList<String> dtTime = new ArrayList<String>();
        ArrayList<String> hst = new ArrayList<>();

        titles.add("dummy1");
        descrips.add("dummy1 descrips");
        loc.add("dummy1 loc");
        dtTime.add("dummy1 dtTime");
        hst.add("dummy1 hst");

        titles.add("dummy2");
        descrips.add("dummy2 descrips");
        loc.add("dummy2 loc");
        dtTime.add("dummy2 dtTime");
        hst.add("dummy1 hst");

        titles.add("dummy3");
        descrips.add("dummy3 descrips");
        loc.add("dummy3 loc");
        dtTime.add("dummy3 dtTime");
        hst.add("dummy1 hst");

        eventAdapter eAdapter = new eventAdapter(titles,descrips, loc, dtTime, hst, this);
        recyclerView.setAdapter(eAdapter);
    }

}
