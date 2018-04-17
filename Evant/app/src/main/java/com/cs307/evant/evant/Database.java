package com.cs307.evant.evant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import com.google.android.gms.maps.model.LatLng;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.lang.Object;

import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Iterator;
import java.util.Map;
import com.google.gson.*;


/**
 * Created by Naomi on 2/23/2018.
 */

public class Database {
    Map<String, Object> users;
    ArrayList<String> titles = new ArrayList();
    ArrayList dttime = new ArrayList();
    ArrayList loc = new ArrayList();
    ArrayList descriptions = new ArrayList();
    ArrayList host = new ArrayList();
    ArrayList lat = new ArrayList();
    ArrayList lng = new ArrayList();
    ArrayList<ArrayList<String>> attendees = new ArrayList();
    ArrayList<String> images = new ArrayList();
    ArrayList<String> categories = new ArrayList();
    FirebaseDatabase db;
    DatabaseReference mDatabase;
    public Database(){
        db = FirebaseDatabase.getInstance();
        mDatabase = db.getReference();
        users = new HashMap<String, Object>();
        init();
    }

    void init(){
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Object result = dataSnapshot.getValue();
                ObjectMapper oMapper = new ObjectMapper();
                users = oMapper.convertValue(result, Map.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("titles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                titles = (ArrayList<String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("dttime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                dttime = (ArrayList<String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("loc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                loc = (ArrayList<String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("description").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                descriptions = (ArrayList<String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("host").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                host = (ArrayList<String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("lat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                lat = (ArrayList<String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("long").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                lng = (ArrayList<String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        /*
        mDatabase.child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                images = (ArrayList<String>) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/
        mDatabase.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                categories = (ArrayList<String>) dataSnapshot.getValue();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("attendees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ArrayList<String> atts = (ArrayList<String>) dataSnapshot.getValue();
                System.out.println("from db: " + atts);
                attendees = toListofLists(atts);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    String getName(String uid){
        if(users.get(uid) == null){
            signOut();
            return null;
        }
        String user =  users.get(uid).toString();
        System.out.println("USER: " + user);
        String name = user.split("name=")[1];
        name = name.substring(0, name.indexOf(","));
        return name;
    }

    ArrayList<Double> getCatTally(String uid){
        if(users.get(uid) == null){
            signOut();
            return null;
        }
        String user = users.get(uid).toString();
        System.out.println(user);
        if(!user.contains("catTally")){
            return null;
        }
        String catTally = user.split("catTally=")[1];
        catTally = catTally.substring(0, catTally.indexOf("]"));
        catTally+="]";
        Gson gson = new Gson();
        ArrayList<Double> tally = gson.fromJson(catTally, ArrayList.class);
        System.out.println(tally);
        return tally;
    }



    ArrayList<String> getTopThree(String uid){
        ArrayList<Double> tally = getCatTally(uid);
        Double first = 0.0, second =0.0, third=0.0;
        int i1 =-1, i2=-1, i3 =-1;

        for(int i = 0; i<tally.size(); i++){
            //System.out.println("tally " + i + " value: "  + tally.get(i));
            if(tally.get(i) > first){
                first = tally.get(i);
                i1 = i;
            }
        }
        //System.out.println("i1 after first loop: " + i1);
        for(int i = 0; i<tally.size(); i++){
            if(tally.get(i) > second){
                if(i != i1){
                    second = tally.get(i);
                    i2 = i;
                }
            }
        }
        for(int i = 0; i<tally.size(); i++){
            if(tally.get(i) > third){
                if(i != i1 && i !=i2) {
                    third = tally.get(i);
                    i3 = i;
                }
            }
        }
        ArrayList<String> results = new ArrayList<>();
        switch (i1){
            case 0:
                results.add("Sports");
                break;
            case 1:
                results.add("Social");
                break;
            case 2:
                results.add("Education");
                break;
            case 3:
                results.add("Gaming");
                break;
            case 4:
                results.add("Community");
                break;
            case 5:
                results.add("Music");
                break;
            case 6:
                results.add("Food");
                break;
            case 7:
                results.add("Art");
                break;
            default:
                break;
        }
        switch (i2){
            case 0:
                results.add("Sports");
                break;
            case 1:
                results.add("Social");
                break;
            case 2:
                results.add("Education");
                break;
            case 3:
                results.add("Gaming");
                break;
            case 4:
                results.add("Community");
                break;
            case 5:
                results.add("Music");
                break;
            case 6:
                results.add("Food");
                break;
            case 7:
                results.add("Art");
                break;
            default:
                break;
        }
        switch (i3){
            case 0:
                results.add("Sports");
                break;
            case 1:
                results.add("Social");
                break;
            case 2:
                results.add("Education");
                break;
            case 3:
                results.add("Gaming");
                break;
            case 4:
                results.add("Community");
                break;
            case 5:
                results.add("Music");
                break;
            case 6:
                results.add("Food");
                break;
            case 7:
                results.add("Art");
                break;
            default:
                break;
        }
        return results;
    }

    void updateCatTally(String uid, ArrayList<Integer> indexes){
        ArrayList<Double> tally = getCatTally(uid);
        if(tally == null){
            ArrayList<Double> newtally = new ArrayList<>();
            newtally.add(0.0);
            newtally.add(0.0);
            newtally.add(0.0);
            newtally.add(0.0);
            newtally.add(0.0);
            newtally.add(0.0);
            newtally.add(0.0);
            newtally.add(0.0);
            tally = newtally;
        }
        System.out.println("indexes: " + indexes);

        for(int i = 0; i<indexes.size(); i++){
            int curr = indexes.get(i);
            String currCat = categories.get(curr);
            if(currCat.contains("Sports")){
                double oldTally = tally.get(0);
                tally.set(0, oldTally+1);
            }
            if(currCat.contains("Social")){
                double oldTally = tally.get(1);
                tally.set(1, oldTally+1);
            }
            if(currCat.contains("Education")){
                double oldTally = tally.get(2);
                tally.set(2, oldTally+1);
            }
            if(currCat.contains("Gaming")){
                double oldTally = tally.get(3);
                tally.set(3, oldTally+1);
            }
            if(currCat.contains("Community")){
                double oldTally = tally.get(4);
                tally.set(4, oldTally+1);
            }
            if(currCat.contains("Music")){
                double oldTally = tally.get(5);
                tally.set(5, oldTally+1);
            }
            if(currCat.contains("Food")){
                double oldTally = tally.get(6);
                tally.set(6, oldTally+1);
            }
            if(currCat.contains("Art")){
                double oldTally = tally.get(7);
                tally.set(7, oldTally+1);
            }
        }
        Gson gson = new Gson();
        String stringt = gson.toJson(tally);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).child("catTally").setValue(stringt);
    }

    void updateInterest(String uid, ArrayList<String> cats){
        Gson gson = new Gson();
        String stringi = gson.toJson(cats);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).child("userDefinedInterests").setValue(stringi);
    }

    ArrayList<String> getInterest(String uid){
        if(users.get(uid) == null){
            signOut();
            return null;
        }
        String user =  users.get(uid).toString();
        //System.out.println("USER: " + user);
        String stringi = user.split("userDefinedInterests=")[1];
        stringi = stringi.substring(0, stringi.indexOf("]"));
        stringi+="]";
        //System.out.println("stringi: " + stringi);
        Gson gson = new Gson();
        ArrayList<String> interest = gson.fromJson(stringi, ArrayList.class);
        return interest;
    }
    void updateName(String uid, String name){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).child("name").setValue(name);
    }

    double getRadius(String uid){
        if(users.get(uid) == null){
            signOut();
            return -1;
        }
        String user =  users.get(uid).toString();
        System.out.println("USER: " + user);
        String name = user.split("radius=")[1];
        name = name.substring(0, name.indexOf(","));
        double r = Double.parseDouble(name);
        System.out.println(uid + "'s radius: " + r);
        return r;
    }

    void updateRadius(String uid, double r){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).child("radius").setValue(Double.toString(r));
    }

    void signUpForEvent(String uid){

    }

    void initializeRating(String uid){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).child("thumbsup").setValue("0");
        mDatabase.child("users").child(uid).child("thumbsdown").setValue("0");
    }

    ArrayList<String> getMyEvents(String uid){
        String user =  users.get(uid).toString();
        //System.out.println("user: " + user);
        String events = user.split("events=")[1];
        events = events.substring(0, events.indexOf("]")+1);
        System.out.println(events);
        Gson gson = new Gson();
        ArrayList liste = gson.fromJson(events, ArrayList.class);
        ArrayList<String> stringe = new ArrayList<>();
        ArrayList<Integer> result = searchByName(liste, stringe);

        //System.out.println("after searchbyname: " + stringe);

        return stringe;
    }

    void updateMyEvents(String uid, ArrayList<String> events){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Gson gson = new Gson();
        String stringe = gson.toJson(events);
        ArrayList<String> dummy = new ArrayList<>();
        ArrayList<Integer> indexes = searchByName(events, dummy);
        for(int i = 0; i<indexes.size(); i++){
            System.out.println("list: " + attendees.get(indexes.get(i)));
            if(attendees.get(indexes.get(i)).contains("none")){
                ArrayList<String> newl = new ArrayList<>();
                newl.add(uid);
                attendees.set(indexes.get(i), newl);
            }else {
                if (!attendees.get(indexes.get(i)).contains(uid)){
                    attendees.get(indexes.get(i)).add(uid);
                }
            }
        }
        System.out.println("whole list after update: " + toListofStrings(attendees));
        mDatabase.child("users").child(uid).child("events").setValue(stringe);
        mDatabase.child("attendees").setValue(toListofStrings(attendees));
    }

    String getUid(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }else{
            signOut();
            return null;
        }
    }

    int getRating(String uid){
        String user =  users.get(uid).toString();
        System.out.println("USER: " + user);
        String upstr = user.split("thumbsup=")[1];

        int thumbsUp;
        try{
            thumbsUp = Integer.parseInt((upstr.substring(0, upstr.indexOf(","))));
        }
        catch(StringIndexOutOfBoundsException e){
            thumbsUp = 0;
        }
        String downstr = user.split("thumbsdown=")[1];
        int thumbsDown;
        try{
            thumbsDown = Integer.parseInt((downstr.substring(0, downstr.indexOf("}"))));
        }
        catch(StringIndexOutOfBoundsException e){
            thumbsDown = 0;
        }
        //INSERT CALCULATION HERE
        int result = thumbsUp - thumbsDown;
        return result;
    }

    void addThumbsUp(String uid){
        String user =  users.get(uid).toString();
        System.out.println(user);
        String upstr = user.split("thumbsup=")[1];
        System.out.println("UP::"+upstr+"::");
        int thumbsUp = Integer.parseInt((upstr.substring(0, upstr.indexOf(","))));
        thumbsUp++;
        System.out.println("New thumbs up: " + thumbsUp);
        String str = Integer.toString(thumbsUp);
        mDatabase.child("users").child(uid).child("thumbsup").setValue(str);
    }

    void addThumbsDown(String uid){
        String user =  users.get(uid).toString();
        System.out.println(user);
        String downstr = user.split("thumbsdown=")[1];
        System.out.println("HERE::"+downstr+"::");
        System.out.println("HERE::" + downstr.indexOf("}"));
        System.out.println((downstr.substring(0, downstr.indexOf("}"))));
        int thumbsDown = Integer.parseInt((downstr.substring(0, downstr.indexOf(","))));
        thumbsDown++;
        System.out.println("New thumbs down: " + thumbsDown);
        String str = Integer.toString(thumbsDown);
        mDatabase.child("users").child(uid).child("thumbsdown").setValue(str);
    }

    void subtractThumbsUp(String uid){
        String user =  users.get(uid).toString();
        System.out.println(user);
        String upstr = user.split("thumbsup=")[1];
        int thumbsUp = Integer.parseInt((upstr.substring(0, upstr.indexOf(","))));
        thumbsUp--;
        System.out.println("New thumbs up: " + thumbsUp);
        String str = Integer.toString(thumbsUp);
        mDatabase.child("users").child(uid).child("thumbsup").setValue(str);
    }

    void subtractThumbsDown(String uid){
        String user =  users.get(uid).toString();
        System.out.println(user);
        String downstr = user.split("thumbsdown=")[1];
        int thumbsDown = Integer.parseInt((downstr.substring(0, downstr.indexOf(","))));
        thumbsDown--;
        System.out.println("New thumbs down: " + thumbsDown);
        String str = Integer.toString(thumbsDown);
        mDatabase.child("users").child(uid).child("thumbsdown").setValue(str);
    }

    LatLng getLocation(String uid){
        if(users.get(uid) == null){
            signOut();
            return null;
        }
        String user =  users.get(uid).toString();
        System.out.println("USER: " + user);
        String plocation = user.split("plocation=")[1];
        plocation = plocation.substring(0, plocation.indexOf("}"));
        String[] latlong =  plocation.split(",");
        LatLng location = new LatLng(Double.parseDouble(latlong[0]), Double.parseDouble(latlong[1]));


        System.out.println(uid + "'s permanent location: " + location);
        return location;
    }

    void updateLocation(String uid, LatLng r){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String slocation = r.latitude + "," + r.longitude;
        mDatabase.child("users").child(uid).child("plocation").setValue(slocation);
    }

    void addEvent(String name, String addr, String desc, String dt, String uid, double latitude, double longitude, Bitmap bm, Map checkButtons){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        titles.add(name);
        loc.add(addr);
        descriptions.add(desc);
        dttime.add(dt);
        host.add(uid);
        ArrayList newAttendees = new ArrayList();
        newAttendees.add(uid);
        attendees.add(newAttendees);
        ArrayList<String> events = getMyEvents(uid);
        events.add(name);
        Gson gson = new Gson();
        String stringe = gson.toJson(events);
        lat.add(latitude);
        lng.add(longitude);
        String category = "";
        Iterator<Map.Entry<String, Boolean>> it = checkButtons.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Boolean> entry = it.next();
            if(entry.getValue() == true){
                category += entry.getKey();
                category += ",";
            }
        }
        categories.add(category);
        if(bm == null){
            images.add("none");
        }else {
            images.add(encodeBitmap(bm));
        }
        mDatabase.child("titles").setValue(titles);
        mDatabase.child("description").setValue(descriptions);
        mDatabase.child("loc").setValue(loc);
        mDatabase.child("dttime").setValue(dttime);
        mDatabase.child("host").setValue(host);
        mDatabase.child("lat").setValue(lat);
        mDatabase.child("long").setValue(lng);
        //mDatabase.child("images").setValue(images);
        mDatabase.child("categories").setValue(categories);
        mDatabase.child("attendees").setValue(toListofStrings(attendees));
        mDatabase.child("users").child(uid).child("events").setValue(stringe);
    }

    String encodeBitmap(Bitmap bm){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    /*
    ArrayList<Bitmap> getImage(){
        ArrayList<Bitmap> bitmaps = new ArrayList();
        for(int i = 0; i<images.size(); i++) {
            String curr = images.get(i);
            byte[] decodedString = Base64.decode(curr, Base64.DEFAULT);
            bitmaps.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        return bitmaps;
    }*/

    ArrayList<String> getTitles(){
        return titles;
    }

    ArrayList<String> getTime(){
        return dttime;
    }

    ArrayList<String> getLoc(){
        return loc;
    }

    ArrayList<String> getDescription(){ return descriptions;}

    ArrayList<String> getHost(){ return host;}

    ArrayList<Double> getLat(){ return lat;}

    ArrayList<Double> getLng(){return lng;}

    ArrayList<ArrayList<String>> getAttendees() { return attendees;}

    ArrayList<String []> getCategories(){
        ArrayList<String []> list = new ArrayList<>();
        for(int i = 0; i<categories.size(); i++){
            String s = categories.get(i);
            System.out.print("String: " + s + " ....... ");
            String [] stringList = s.split(",");
            System.out.println("Strings: " + Arrays.toString(stringList));
            list.add(stringList);
        }
        System.out.println("Final list of arrays: " + list);
        return list;
    }

    ArrayList<Integer> searchByName (ArrayList<String> names, ArrayList<String> results){
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0; i<titles.size(); i++){
            String currTitle = titles.get(i);
            //System.out.println("currtitle: " + currTitle);
            for(int j = 0; j<names.size(); j++) {
                if (currTitle.contains(names.get(j))) {
                    //System.out.println("match: " + names.get(j));
                    indexes.add(i);
                    results.add(currTitle);
                }
            }
        }
        return indexes;
    }

    ArrayList<Integer> search(String criteria, Location currLoc, double radius){
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0; i<lat.size(); i++){
            final Object latObj = lat.get(i);
            final Object lngObj = lng.get(i);
            final double douLat = ((Number) latObj).doubleValue();
            final double douLng = ((Number) lngObj).doubleValue();

            LatLng tempLatLng = new LatLng(douLat, douLng);
            if(calculateDistance(tempLatLng, currLoc) <= radius){
                indexes.add(i);
            }
        }

        System.out.println("Indices in radius: " + indexes);

        if(!criteria.equals("")){
            ArrayList<Integer> searchIndexes = new ArrayList<>();
            for(int ind: indexes){

                String currTitle = titles.get(ind).toLowerCase();
                System.out.println(ind);
                //System.out.println("currtitle: " + currTitle);
                if (currTitle.contains(criteria.toLowerCase())) {
                        //System.out.println("match: " + names.get(j));
                        searchIndexes.add(ind);

                    }

            }
            return searchIndexes;
        }

        return indexes;
    }

    void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    private double calculateDistance(LatLng marLoc, Location currLoc) {
        Location curLoc = currLoc;

        //Location curLoc = new Location("");
        //curLoc.setLatitude(40.427728);
        //curLoc.setLongitude(-86.947603);


        Location markerLoc = new Location("");
        markerLoc.setLatitude(marLoc.latitude);
        markerLoc.setLongitude(marLoc.longitude);

        double distance = -1;

        try {
            distance = curLoc.distanceTo(markerLoc);
        } catch (NullPointerException e) {
            //Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT).show();
        }

        distance = distance / 1000;


        return distance;
    }

    ArrayList toListofLists(ArrayList<String> atts){
        ArrayList builder = new ArrayList();
        System.out.println(atts);
        for(int i = 0; i<atts.size(); i++){
            //System.out.println("list: " + atts.get(i));
            Gson gson = new Gson();
            builder.add(gson.fromJson(atts.get(i), ArrayList.class));
        }
        System.out.println("result of toListofLists: " + builder);
        return builder;
    }

    ArrayList<String> toListofStrings(ArrayList list){
        ArrayList<String> builder = new ArrayList<>();
        for(int i = 0; i<list.size(); i++){
            Gson gson = new Gson();
            String stringe = gson.toJson(list.get(i));
            builder.add(stringe);
        }
        return builder;
    }
}