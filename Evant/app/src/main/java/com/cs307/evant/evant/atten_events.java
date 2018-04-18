package com.cs307.evant.evant;

import android.location.Location;
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

import com.google.android.gms.maps.model.LatLng;

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

                    sortMyEvents(1);
                }
                else {
                    compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    compoundButton.setTextColor(getResources().getColor(R.color.colorPrimary));

                    sortMyEvents(0);
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
                    sortMyEvents(3);
                }
                else {
                    compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    compoundButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                    sortMyEvents(2);
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
                    sortMyEvents(4);
                }
                else {
                    compoundButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    compoundButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                    sortMyEvents(3);
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
        ArrayList<Double> myLat = new ArrayList<>();
        ArrayList<Double> myLng = new ArrayList<>();

        ArrayList<String> myAttended = new ArrayList<>();
        ArrayList<String> titles = db.getTitles();
        ArrayList<String> descrips = db.getDescription();
        ArrayList<String> loc = db.getLoc();
        ArrayList<String> dtTime = db.getTime();
        ArrayList<String> hst = db.getHost();
        ArrayList<Double> lat = db.getLat();
        ArrayList<Double> lng = db.getLng();

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
                        myLat.add(lat.get(b));
                        myLng.add(lng.get(b));
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

        eventAdapter eAdapter = new eventAdapter(myAttended,myDescrips, myLoc, myTime, myHst, myLat, myLng, this);
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
    private void sortMyEvents(int sortby){
        ArrayList<String> myEvents = db.getMyEvents(db.getUid());
        System.out.println(":::::" + myEvents.size());
        ArrayList<String> myDescrips = new ArrayList<>();
        ArrayList<String> myLoc = new ArrayList<>();
        ArrayList<String> myTime = new ArrayList<>();
        ArrayList<String> myHst = new ArrayList<>();
        ArrayList<String> myAttended = new ArrayList<>();
        ArrayList<Double> myLat = new ArrayList<>();
        ArrayList<Double> myLng = new ArrayList<>();

        ArrayList<String> titles = db.getTitles();
        ArrayList<String> descrips = db.getDescription();
        ArrayList<String> loc = db.getLoc();
        ArrayList<String> dtTime = db.getTime();
        ArrayList<String> hst = db.getHost();
        ArrayList<Double> lat = db.getLat();
        ArrayList<Double> lng = db.getLng();

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
                        myLat.add(lat.get(b));
                        myLng.add(lng.get(b));
                    }
                }
            }
        }

        bubbleSort(myDescrips, myLoc, myTime, myHst, myAttended, myLat, myLng, sortby);

        eventAdapter eAdapter = new eventAdapter(myAttended,myDescrips, myLoc, myTime, myHst, myLat, myLng, this);
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
    static void bubbleSort(ArrayList<String> myDescrips, ArrayList<String> myLoc, ArrayList<String> myTime, ArrayList<String> myHst, ArrayList<String> myAttended, ArrayList<Double> myLat, ArrayList<Double> myLng, int sortby)
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
                   //
                    //swap if title by title less
                    System.out.println(myAttended.get(j) + ":" + myAttended.get(j));
                    System.out.println(myAttended.get(j +1) + ":" + myAttended.get(j + 1));
                    if (myAttended.get(j).compareToIgnoreCase(myAttended.get(j + 1)) < 0)
                    {
                        // swap j and j + 1 for all arrays
                        Collections.swap(myDescrips, j, j+1);
                        Collections.swap(myLoc, j, j+1);
                        Collections.swap(myTime, j, j+1);
                        Collections.swap(myHst, j, j+1);
                        Collections.swap(myAttended, j, j+1);
                    }
                }
                if (sortby == 1){
                    //swap if title by title greater
                    System.out.println(myAttended.get(j) + ":" + myAttended.get(j));
                    System.out.println(myAttended.get(j +1) + ":" + myAttended.get(j + 1));
                    if (myAttended.get(j).compareToIgnoreCase(myAttended.get(j + 1)) >= 0)
                    {
                        // swap j and j + 1 for all arrays
                        Collections.swap(myDescrips, j, j+1);
                        Collections.swap(myLoc, j, j+1);
                        Collections.swap(myTime, j, j+1);
                        Collections.swap(myHst, j, j+1);
                        Collections.swap(myAttended, j, j+1);
                    }
                }
                if (sortby == 2){
                    System.out.println(myTime.get(j) + ":" + parsedate(myTime.get(j)));
                    System.out.println(myTime.get(j +1) + ":" + parsedate(myTime.get(j + 1)));
                    if (parsedate(myTime.get(j)) > parsedate(myTime.get(j + 1)))
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
                    System.out.println("myLat = " + myLat.get(j)  + " my Lng = " + myLng.get(j));
                    LatLng curLoc = db.getLocation(db.getUid());
                    Location cur = new Location("cur");
                    cur.setLatitude(curLoc.latitude);
                    cur.setLongitude(curLoc.longitude);
                    LatLng tempLoc = new LatLng(myLat.get(j), myLng.get(j));
                    Location temp = new Location("temp");
                    temp.setLatitude(tempLoc.latitude);
                    temp.setLongitude(tempLoc.longitude);
                    LatLng tempLoc2 = new LatLng(myLat.get(j+1), myLng.get(j+1));
                    Location temp2 = new Location("temp2");
                    temp2.setLatitude(tempLoc2.latitude);
                    temp2.setLongitude(tempLoc2.longitude);
                    System.out.println("distance 1 = " + cur.distanceTo(temp) + " distance 2 = " + cur.distanceTo(temp2));
                    if(cur.distanceTo(temp) > cur.distanceTo(temp2)){
                        Collections.swap(myDescrips, j, j+1);
                        Collections.swap(myLoc, j, j+1);
                        Collections.swap(myTime, j, j+1);
                        Collections.swap(myHst, j, j+1);
                        Collections.swap(myAttended, j, j+1);
                    }

                }
                if (sortby == 5){
                    //TODO swap if distance less
                    System.out.println("myLat = " + myLat.get(j)  + " my Lng = " + myLng.get(j));
                    LatLng curLoc = db.getLocation(db.getUid());
                    Location cur = new Location("cur");
                    cur.setLatitude(curLoc.latitude);
                    cur.setLongitude(curLoc.longitude);
                    LatLng tempLoc = new LatLng(myLat.get(j), myLng.get(j));
                    Location temp = new Location("temp");
                    temp.setLatitude(tempLoc.latitude);
                    temp.setLongitude(tempLoc.longitude);
                    LatLng tempLoc2 = new LatLng(myLat.get(j+1), myLng.get(j+1));
                    Location temp2 = new Location("temp2");
                    temp2.setLatitude(tempLoc2.latitude);
                    temp2.setLongitude(tempLoc2.longitude);
                    System.out.println("distance 1 = " + cur.distanceTo(temp) + " distance 2 = " + cur.distanceTo(temp2));
                    if(cur.distanceTo(temp) <= cur.distanceTo(temp2)){
                        Collections.swap(myDescrips, j, j+1);
                        Collections.swap(myLoc, j, j+1);
                        Collections.swap(myTime, j, j+1);
                        Collections.swap(myHst, j, j+1);
                        Collections.swap(myAttended, j, j+1);
                    }

                }


            }
            // IF no two elements were
            // swapped by inner loop, then break
            /*if (swapped == false)
                break;*/
        }
    }


    //04/04/2018   14:40 PM
    //03/25/18   03:09 PM
    // takes in a date as a string and produces a number value of the date
    // result is a 10 digit to 12 int in the form of YYYYMMDDHHMM
    static private double parsedate(String date){
        // storage values
        int sec = 0, min = 0, hr = 0, day = 0, month = 0, year = 0;

        // first lets get the date
        for (int i = 0; i < date.length(); i++){
            if (charIsSlash(date.charAt(i))){

                // for later
                int numberOfMonthDigits;
                int numberOfDayDigits = 0;
                int numberOfYearDigits = 0;

                // we found a slash
                // check that it is valid
                if (i - 1 < 0){
                    // then it is not far enough into the string to have a date yet
                    continue;
                }
                // if one digit back is not a number it is bad
                if (!Character.isDigit(date.charAt(i-1))){
                    continue;
                }

                // find number of month digits
                numberOfMonthDigits = 2;
                if (i - 2 < 0) {
                    numberOfMonthDigits = 1;
                } else {
                    if (!Character.isDigit(date.charAt(i-2))){
                        numberOfMonthDigits = 1;
                    }
                }


                //find number of day digits
                // check that our next question isn't out of bounds
                if (i + 3 < date.length()){
                    // find out if there is a slash one or to away
                    if (charIsSlash(date.charAt(i+2))){
                        // there is a second slash we have XX/X/XXXX
                        numberOfDayDigits = 1;

                    } else if (charIsSlash(date.charAt(i+3))){
                        // there is a second slash we have XX/XX/XXXX
                        numberOfDayDigits = 2;
                    } else {
                        // ther is too many digits for the day
                        continue;
                    }
                }

                //find number of day digits
                for (int j = 0; j < 4; j++){
                    if (i + numberOfDayDigits + 2 + j < date.length()){
                        if (Character.isDigit(date.charAt(i + numberOfDayDigits + 2 + j))){
                            numberOfYearDigits++;
                        }
                    }
                }
                // check we dont have too few
                if (numberOfYearDigits < 2 || numberOfYearDigits == 3){
                    continue;
                }

                // if we get here we have a good date
                // set month
                month = Character.getNumericValue(date.charAt(i-1));
                if (numberOfMonthDigits == 2){
                    month += Character.getNumericValue(date.charAt(i-2))*10;
                }
                //set day
                day = Character.getNumericValue(date.charAt(i+1));
                if (numberOfDayDigits == 2){
                    day = Character.getNumericValue(date.charAt(i+2)) + day*10;
                }
                //set year
                year = Character.getNumericValue(date.charAt(i + numberOfDayDigits + 2));
                year = Character.getNumericValue(date.charAt(i + numberOfDayDigits + 3)) + year*10;
                if (numberOfYearDigits == 4){
                    year = Character.getNumericValue(date.charAt(i + numberOfDayDigits + 4)) + year*10;
                    year = Character.getNumericValue(date.charAt(i + numberOfDayDigits + 5)) + year*10;
                } else {
                    year += 2000;
                }

                break;

            }
        }

        // find the time
        for (int i = 0; i < date.length(); i++){
            if (date.charAt(i) == ':') {
                // check if enough characters before and behind
                if (i - 2 > 0 && i + 2 < date.length()){
                    // check that they are all digits
                    if (Character.isDigit(date.charAt(i-2)) && Character.isDigit(date.charAt(i-1)) && Character.isDigit(date.charAt(i+1)) && Character.isDigit(date.charAt(i+2))){
                        // set the min and hr
                        hr = Character.getNumericValue(date.charAt(i-2))*10 + Character.getNumericValue(date.charAt(i-1));
                        min = Character.getNumericValue(date.charAt(i+1))*10 + Character.getNumericValue(date.charAt(i+2));

                        if (date.toLowerCase().contains("pm")){
                            hr += 12;
                        }
                    }

                }


            }

        }

        return min + hr*100 + day*10000 + month*1000000 + ((double)year)*100000000;
        // TODO (Adam) improve this
    }

    static private boolean charIsSlash(char c){
        if (c == '/' ||  c == '\\' || c == '-'){
            return true;
        } else {
            return false;
        }
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

      //  eventAdapter eAdapter = new eventAdapter(titles,descrips, loc, dtTime, hst, this);
       // recyclerView.setAdapter(eAdapter);
    }

}
