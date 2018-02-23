package com.cs307.evant.evant;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.lang.Object;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Naomi on 2/23/2018.
 */

public class Database {
    Map<String, Object> users;
    ArrayList titles = new ArrayList();
    ArrayList dttime = new ArrayList();
    ArrayList loc = new ArrayList();
    ArrayList descriptions = new ArrayList();
    ArrayList host = new ArrayList();
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
    }

    String getName(String uid){
        String user =  users.get(uid).toString();
        String name = user.split("name=")[1];
        name = name.substring(0, name.indexOf("}"));
        return name;
    }

    void updateName(String uid, String name){
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).child("name").setValue(name);
    }

    String getUid(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    void addEvent(String name, String addr, String desc, String dt){
        String uid = getUid();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //System.out.println(titles);
        titles.add(name);
        loc.add(addr);
        descriptions.add(desc);
        dttime.add(dt);
        host.add(uid);
        mDatabase.child("titles").setValue(titles);
        mDatabase.child("description").setValue(descriptions);
        mDatabase.child("loc").setValue(loc);
        mDatabase.child("dttime").setValue(dttime);
        mDatabase.child("host").setValue(host);
    }

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

    void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

}
