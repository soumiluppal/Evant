package com.cs307.evant.evant;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by avi12 on 2/22/2018.
 */

public class catList extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cat_lst);
        recyclerView = (RecyclerView) findViewById(R.id.categories);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String>ctnms = new ArrayList<>();
        ctnms.add("Sports");
        ctnms.add("Social");
        ctnms.add("Education");
        ctnms.add("Video Games");
        ctnms.add("Community");
        ctnms.add("Food");
        ctnms.add("Flash Mobs");
        catAdapter cadapter = new catAdapter(ctnms,this);
        recyclerView.setAdapter(cadapter);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
