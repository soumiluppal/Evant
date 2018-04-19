package com.cs307.evant.evant;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by avi12 on 2/22/2018.
 */

public class catList extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cat_lst);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button button = findViewById(R.id.homebutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(catList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button profileButton = (Button) findViewById(R.id.profilebutton);



        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(catList.this, Profile.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

        //attempt
        Button settingsButton = (Button) findViewById(R.id.settingsbutton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(catList.this, Settings.class);
                intent.putExtra("uid", db.getUid());
                startActivity(intent);
            }
        });

        final EditText sbar = findViewById(R.id.searchBar);

        sbar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    //Toast.makeText(catList.this, "Works?", Toast.LENGTH_LONG).show();
                    Intent intnt = new Intent(catList.this,eventList.class);
                    intnt.putExtra("Searching","true");
                    intnt.putExtra("Word",sbar.getText().toString());
                    startActivity(intnt);
                }
                return false;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.categories);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String>ctnms = new ArrayList<>();
        ctnms.add("Sports");
        ctnms.add("Social");
        ctnms.add("Education");
        ctnms.add("Gaming");
        ctnms.add("Community");
        ctnms.add("Music");
        ctnms.add("Food");
        ctnms.add("Art");
        ctnms.add("Suggested");
        ctnms.add("Others");

        catAdapter cadapter = new catAdapter(ctnms,this);
        recyclerView.setAdapter(cadapter);

        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        final FloatingActionsMenu fam = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        //fam.addButton(actionC);
        FloatingActionButton create = (FloatingActionButton) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapView.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(catList.this, NewEventActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton attend = (FloatingActionButton) findViewById(R.id.attend);
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapView.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(catList.this, catList.class);
                startActivity(intent);
            }
        });

        fam.bringToFront();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
