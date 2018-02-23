package com.cs307.evant.evant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by avi12 on 2/22/2018.
 */

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.MyViewHolder>{
    private ArrayList<Bitmap> catPhotos;
    private ArrayList<String> titles;
    private ArrayList<String> descrip;
    private ArrayList<String> dttme;
    private ArrayList<String> location;

    private Context ct;
    //private ImageView iv;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView descrip;
        public TextView dtm;
        public TextView loca;
        public ImageView iv;
        public Button info;
        //public TextView cnts;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.eventTitle);
            descrip = view.findViewById(R.id.description);
            dtm = view.findViewById(R.id.time);
            loca = view.findViewById(R.id.locationText);
            info = view.findViewById(R.id.minfo);


            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ct, eventPage.class);
                    ct.startActivity(intent);
                }
            });
            //cnts = (TextView) view.findViewById(R.id.count);
            //iv = (ImageView) view.findViewById(R.id.photo);


        }
    }

    public eventAdapter( ArrayList <String> title, ArrayList <String> descrp, ArrayList <String> dte, ArrayList <String> loc,Context c)
    {
        //catPhotos = a;
        titles = title;
        descrip = descrp;
        dttme = dte;
        location = loc;
        ct = c;
    }

    @Override
    public eventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ct = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);



        return new eventAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(eventAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(titles.get(position));
        holder.loca.setText(location.get(position));
        holder.dtm.setText(dttme.get(position));
        holder.descrip.setText(descrip.get(position));
        //Drawable myDrawable = getApplicationContext().getResources().getDrawable(R.drawable.shoebackground);
        //String fpath = photopath.get(position);
        //holder.iv.setImageBitmap(catPhotos.get(position));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, eventPage.class);
                ct.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return titles.size();
    }
}

