package com.cs307.evant.evant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.lang.Object;

import java.util.ArrayList;

import java.util.Arrays;
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

    void initializeRating(String uid){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).child("thumbsup").setValue("0");
        mDatabase.child("users").child(uid).child("thumbsdown").setValue("0");
    }

    ArrayList<String> getMyEvents(String uid){

        String user =  users.get(uid).toString();
        System.out.println(user);
        String events = user.split("events=")[1];
        events = events.substring(0, events.indexOf("}"));
        Gson gson = new Gson();
        ArrayList liste = gson.fromJson(events, ArrayList.class);
        ArrayList<String> stringe = new ArrayList<>();
        ArrayList<Integer> result = searchByName(liste, stringe);


        return stringe;
    }

    void updateMyEvents(String uid, ArrayList<String> events){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Gson gson = new Gson();
        String stringe = gson.toJson(events);
        mDatabase.child("users").child(uid).child("events").setValue(stringe);
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
        int thumbsDown = Integer.parseInt((downstr.substring(0, downstr.indexOf("}"))));
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

    void addEvent(String name, String addr, String desc, String dt, String uid, double latitude, double longitude, Bitmap bm, Map checkButtons){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        titles.add(name);
        loc.add(addr);
        descriptions.add(desc);
        dttime.add(dt);
        host.add(uid);
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
        mDatabase.child("images").setValue(images);
        mDatabase.child("categories").setValue(categories);
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
            for(int j = 0; j<names.size(); j++) {
                if (currTitle.contains(names.get(j))) {
                    indexes.add(i);
                    results.add(currTitle);
                }
            }
        }
        return indexes;
    }

    void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

}