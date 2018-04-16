package com.cs307.evant.evant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.cs307.evant.evant.MainActivity.db;

/**
 * Created by avi12 on 2/22/2018.
 */

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.MyViewHolder>{
    private ArrayList<Bitmap> catPhotos;
    private ArrayList<String> titles;
    private ArrayList<String> descrip;
    private ArrayList<String> dttme;
    private ArrayList<String> location;
    private ArrayList<String> hosts;

    private Context ct;
    //private ImageView iv;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView descrip;
        public TextView dtm;
        public TextView loca;
        public ImageView iv;
        public Button info;
        public Button jEvent;
        //private ArrayList<String> hosts;
        //public TextView cnts;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.eventTitle);
            descrip = view.findViewById(R.id.description);
            dtm = view.findViewById(R.id.time);
            loca = view.findViewById(R.id.locationText);
            info = view.findViewById(R.id.minfo);
            jEvent = view.findViewById(R.id.joinEvent);



            //cnts = (TextView) view.findViewById(R.id.count);
            //iv = (ImageView) view.findViewById(R.id.photo);


        }
    }

    public eventAdapter( ArrayList <String> title, ArrayList <String> descrp, ArrayList <String> dte, ArrayList <String> loc, ArrayList <String> h, Context c)
    {
        //catPhotos = a;
        titles = title;
        descrip = descrp;
        dttme = dte;
        location = loc;
        hosts = h;
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

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ct, eventPage.class);
                System.out.println("HOSTS IN ADAPTER: " + hosts.get(position));
                intent.putExtra("Host",hosts.get(position));
                intent.putExtra("Title",titles.get(position));
                intent.putExtra("Description",descrip.get(position));
                intent.putExtra("dttime",dttme.get(position));
                intent.putExtra("location",location.get(position));
                ct.startActivity(intent);
            }
        });

        holder.jEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = db.getUid();
                //System.out.println(uid);
                ArrayList<String> myEvents = db.getMyEvents(db.getUid());
                ArrayList<String> events = db.getTitles();
                if(!myEvents.contains(titles.get(position))) {
                    myEvents.add(titles.get(position));
                    ArrayList<String> newOne = new ArrayList<>();
                    ArrayList<String> dummy = new ArrayList<>();
                    newOne.add(titles.get(position));
                    db.updateCatTally(uid, db.searchByName(newOne, dummy));
                    db.updateMyEvents(uid,myEvents);
                    Toast.makeText(ct, "Successfully Joined Event!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ct, "Already attending this Event.", Toast.LENGTH_LONG).show();
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, eventPage.class);

                intent.putExtra("Host",hosts.get(position));
                intent.putExtra("Title",titles.get(position));
                intent.putExtra("Description",descrip.get(position));
                intent.putExtra("dttime",dttme.get(position));
                intent.putExtra("location",location.get(position));

                ct.startActivity(intent);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), "success", Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.menu01:
                                db.addThumbsUp(hosts.get(position));
                                return true;
                            case R.id.menu02:
                                db.addThumbsDown(hosts.get(position));
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();

                return true;
            }
        });



    }


    @Override
    public int getItemCount() {
        return titles.size();
    }
}

