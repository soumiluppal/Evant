package com.cs307.evant.evant;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

import static com.cs307.evant.evant.MainActivity.db;

public class Profile extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String uid = b.getString("uid");
        setContentView(R.layout.activity_profile);
        String
                name = db.getName(db.getUid());
        TextView dispname = (TextView) findViewById(R.id.dispname);
        dispname.setText("Name: " + name);

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
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
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

        eventAdapter adapter = new eventAdapter(ntitles,ndesc,ndttime,nloc,this);


        recyclerView.setAdapter(adapter);

        ArrayList<String> interests = new ArrayList<>(5);

        for(int i = 0; i < 5; i++) {
            interests.add("Interest " + (i+1));
        }
        LinearLayout intLayout = (LinearLayout)findViewById(R.id.linearLayout);
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
        /*for(int i = 0; i < interests.size(); i++) {

        }*/
    }
}
