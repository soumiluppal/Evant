package com.cs307.evant.evant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.cs307.evant.evant.MainActivity.db;

public class Profile extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> ins = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> myEvents = new ArrayList<>();
        myEvents = db.getMyEvents(db.getUid());
        //ArrayList<String[]> ints = db.getCategories();
        //Toast.makeText(getApplicationContext(), "GETMYEVEENT: " +  myEvents, Toast.LENGTH_LONG).show();
        Bundle b = getIntent().getExtras();
        String uid = b.getString("uid");
        setContentView(R.layout.activity_profile);
        String name = db.getName(db.getUid());
        TextView dispname = (TextView) findViewById(R.id.dispname);
        TextView intrst = findViewById(R.id.interestsText);
        TextView topCats = findViewById(R.id.topCats);

        ArrayList<String> top3 = db.getTopThree(db.getUid());
        String topCat = "";

        int n = top3.size();
        if(n==1){
            topCat = top3.get(0);
        }else {
            for (int i = 0; i < n - 1; i++) {
                topCat += top3.get(i) + ", ";
            }
            topCat += top3.get(n - 1);
        }

        String interets = topCat;

        ArrayList<Integer> indxs = new ArrayList<>();




        indxs = db.searchByName(myEvents,new ArrayList<String>());
        ArrayList<String[]> cats = new ArrayList<>();
        cats = db.getCategories();
        SQLiteOpenHelper DatabaseHelper = new DataHelp(Profile.this);
        SQLiteDatabase dbs = DatabaseHelper.getReadableDatabase();
        //Cursor cursor = dbs.query("LOGINDATA", new String[]{"INTRST"}, null, null, null, null, "_id DESC");
        //cursor.moveToFirst();
        ContentValues cv = new ContentValues();


        for(int i = 0; i < indxs.size();i++)
        {
            String tmp;
            //System.out.println(cats.get(indxs.get(i))[0]);
            tmp = cats.get(indxs.get(i))[0];
            if(!ins.contains(tmp))
            {
                if(interstNContains(tmp))
                {
                    ins.add(tmp);
                    cv = new ContentValues();
                    cv.put("INTRST",tmp);
                    dbs.insert("LOGINDATA",null,cv);
                }

            }

        }
        //dbs.insert("LOGINDATA",null,cv);
        dbs.close();


        //SQLiteOpenHelper DatabaseHelper = new DataHelp(loginPage.this);
        //SQLiteDatabase db = DatabaseHelper.getReadableDatabase();
        //cursor = db.query("LOGINDATA", new String[]{"CAT", "PATH","CNT","ROTATE","IMAGE"}, null, null, null, null, "_id DESC");

        //db.delete("LOGINDATA", null, null);

        //ContentValues cv = new ContentValues();

        //cv.put("USER",user);
        //cv.put("PASS", paswrd);
        //cv.put("LOGGED",1);
        //db.insert("LOGINDATA",null,cv);
        //db.close();
        //String intrs = interets.replaceAll("]","");
        //String tmp = "[";
        //String fintrs = intrs.replaceAll(tmp,"");
        //intrst.setText(intrs);


        dispname.setText("Name: " + name);

        Button button = findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Button profileButton = (Button) findViewById(R.id.profilebutton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Profile.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

        //attempt
        Button settingsButton = (Button) findViewById(R.id.settingsbutton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Settings.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton actionC = new com.getbase.floatingactionbutton.FloatingActionButton(getBaseContext());
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        final FloatingActionsMenu fam = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        //fam.addButton(actionC);
        com.getbase.floatingactionbutton.FloatingActionButton create = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapView.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Profile.this, NewEventActivity.class);
                startActivity(intent);
            }
        });

        com.getbase.floatingactionbutton.FloatingActionButton attend = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.attend);
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapView.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Profile.this, catList.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.events);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> dttime = new ArrayList<>();
        ArrayList<String> loc = new ArrayList<>();
        ArrayList<String> descrip = new ArrayList<>();
        ArrayList<String> host = new ArrayList<>();

        titles = db.getTitles();
        dttime = db.getTime();
        loc = db.getLoc();
        descrip = db.getDescription();
        host = db.getHost();

        ArrayList<String> ntitles = new ArrayList<>();
        ArrayList<String> ndttime = new ArrayList<>();
        ArrayList<String> nloc = new ArrayList<>();
        ArrayList<String> ndesc = new ArrayList<>();
        ArrayList<String> nhost = new ArrayList<>();
        System.out.println(titles);
        for(int i = 0; i<host.size(); i++){
            if(host.get(i).equals(uid)){
                ntitles.add(titles.get(i));
                ndttime.add(dttime.get(i));
                nloc.add(loc.get(i));
                ndesc.add(descrip.get(i));
                nhost.add(host.get(i));
            }
        }

        ArrayList<String> ctnms = new ArrayList<>();
        ctnms.add("event1");
        ctnms.add("event2");
        Collections.reverse(ntitles);
        Collections.reverse(ndesc);
        Collections.reverse(ndttime);
        Collections.reverse(nloc);
        Collections.reverse(nhost);
        eventAdapter adapter = new eventAdapter(ntitles,ndesc,ndttime,nloc, nhost, this);


        recyclerView.setAdapter(adapter);

        ArrayList<String> interests = new ArrayList<>();

        interests = getInterst();
        String cck = interests.get(1);
        ins.remove("\n");
        interests = ins;

        TextView intin = findViewById(R.id.interestsText);
        String wholeintrst = "";
        for(int i = 0; i < ins.size(); i++)
        {
            if(ins.get(i) != null)
                wholeintrst += ins.get(i) + "  ";
        }
        intin.setText(wholeintrst);
        topCats.setText("Top Categories: " + topCat);
        /*LinearLayout intLayout = (LinearLayout)findViewById(R.id.linearLayout);
        intLayout.setOrientation(LinearLayout.VERTICAL);
        TextView text = null;
        for(int i = 0; i < interests.size(); i++) {
            text = new TextView(Profile.this);
            text.setText(interests.get(i));
            text.setId(i);
            intLayout.setLayoutParams(intLayout.getLayoutParams());
            text.setGravity(Gravity.CENTER);
            intLayout.addView(text);
        }
        */

    }

    public boolean interstNContains(String str)
    {
        SQLiteOpenHelper DatabaseHelper = new DataHelp(Profile.this);
        SQLiteDatabase dbs = DatabaseHelper.getReadableDatabase();
        Cursor cursor = dbs.query("LOGINDATA", new String[]{"INTRST"}, null, null, null, null, "_id DESC");
        //cursor.moveToFirst();
        String tmp;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            if(cursor.getString(0) == null)
                cursor.moveToNext();
            if(cursor.getString(0) == null)
                break;
            if(cursor.getString(0).equals(str))
            {
                return false;
            }
        }
        cursor.close();
        dbs.close();
        return true;
    }

    public ArrayList<String> getInterst()
    {
        ArrayList<String> ans = new ArrayList<>();
        SQLiteOpenHelper DatabaseHelper = new DataHelp(Profile.this);
        SQLiteDatabase dbs = DatabaseHelper.getReadableDatabase();
        Cursor cursor = dbs.query("LOGINDATA", new String[]{"INTRST"}, null, null, null, null, "_id DESC");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            ans.add(cursor.getString(0));
            System.out.println("tmp cursor: " + cursor.getString(0));
            if(!ins.contains(cursor.getString(0)))
            {
                ins.add(cursor.getString(0));
            }
        }

        cursor.close();

        //dbs.close();
        return ans;
    }
}
