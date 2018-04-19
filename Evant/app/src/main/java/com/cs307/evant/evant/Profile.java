package com.cs307.evant.evant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String uid = b.getString("uid");
        setContentView(R.layout.activity_profile);
        String name = db.getName(uid);
        TextView dispname = (TextView) findViewById(R.id.dispname);
        TextView intrst = findViewById(R.id.interestsText);
        TextView topCats = findViewById(R.id.topCats);

        ArrayList<String> top3 = db.getTopThree(uid);
        String topCat = "";

        int n = top3.size();
        if(n == 0) {

        }
        else if(n==1){
            topCat = top3.get(0);
        }else {
            for (int i = 0; i < n - 1; i++) {
                topCat += top3.get(i) + ", ";
            }
            topCat += top3.get(n - 1);
        }



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

        Button fbButton = (Button) findViewById(R.id.fbButton);

        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://facebook.com/" + db.getFB(db.getUid()));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        TextView rating = (TextView) findViewById(R.id.hostRating);

        rating.append(" " + Integer.toString(db.getRating(uid)));

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
        ArrayList<Double> lats = new ArrayList<>();
        ArrayList<Double> lngs = new ArrayList<>();
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
        eventAdapter adapter = new eventAdapter(ntitles,ndesc,ndttime,nloc, nhost, lats, lngs, this);



        recyclerView.setAdapter(adapter);

        TextView intin = findViewById(R.id.interestsText);
        String wholeintrst = "";
        ArrayList<String> ins = db.getInterest(db.getUid());
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
}
