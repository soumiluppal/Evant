package com.cs307.evant.evant;

/**
 * Created by avi12 on 2/18/2018.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Avi Agarwal on 8/11/2016.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private ArrayList<Bitmap> catPhotos;
    private ArrayList<String> cattitles;
    private ArrayList<Bitmap> itemPhotos;
    private ArrayList<String> itemTitles;
    private ArrayList<String> itemPaths;
    private ArrayList<String> itemCat;
    private ArrayList<Integer> cnt;
    private RecyclerView currView;
    private Context ct;
    //private ImageView iv;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView iv;
        public TextView cnts;

        public MyViewHolder(View view)
        {
            super(view);
            //title = (TextView) view.findViewById(R.id.titwork);
            //cnts = (TextView) view.findViewById(R.id.count);
            //iv = (ImageView) view.findViewById(R.id.photomain);

        }
    }


    public MainAdapter(ArrayList<Bitmap> a, ArrayList <String> b,ArrayList<Integer> e)
    {
        catPhotos = a;
        cattitles = b;
        cnt = e;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ct = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sign_up, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(cattitles.get(position));
        //Drawable myDrawable = getApplicationContext().getResources().getDrawable(R.drawable.shoebackground);
        //String fpath = photopath.get(position);
        holder.iv.setImageBitmap(catPhotos.get(position));
        String cntz = cnt.get(position) + " Items in Category";
        holder.cnts.setText(cntz);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currcat = cattitles.get(position);
                //launchPhotos(currcat);
                //Intent i = new Intent(ct,photoViewer.class);
                //i.putExtra("category",cattitles.get(position));
                //i.putStringArrayListExtra("catlist",cattitles);
               // ct.startActivity(i);
                //int i;
                //ArrayList<Bitmap> passPhotos = new ArrayList<Bitmap>();
                //ArrayList<String> passNames = new ArrayList<String>();
                //ArrayList<String> passPaths = new ArrayList<String>();
                //for(i =0; i < itemTitles.size();i++)
                //{
                //    if(itemCat.get(i).equals(cattitles.get(position)))
                //    {
                //        passPhotos.add(itemPhotos.get(i));
                //        passNames.add(itemTitles.get(i));
                //        passPaths.add(itemPaths.get(i));
                //    }
                //}
                //currView.setVisibility(View.v);
                //photoAdapter pa = new photoAdapter(passPhotos,passNames,cattitles.get(position),passPaths);
                //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ct, 2);
                //currView.setLayoutManager(mLayoutManager);
                //currView.setHasFixedSize(true);
                //currView.addItemDecoration(new photoDecorator(ct, LinearLayoutManager.VERTICAL));
                //currView.setItemAnimator(new DefaultItemAnimator());
                //currView.setAdapter(pa);

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(ct);
                build.setTitle("Delete " + cattitles.get(position) + " Category? All items will be lost.");
                AlertDialog.Builder builder = build.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cattitles.get(position).equals("Outfits"))
                        {
                            Toast toast = Toast.makeText(ct, "Sorry the Outfits Category cannot be deleted", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else
                        {
                            //SQLiteOpenHelper DatabaseHelper = new DataHelp(ct);
                            //SQLiteDatabase db = DatabaseHelper.getReadableDatabase();
                            //db.delete("CATDATA", "CAT = ?", new String[]{cattitles.get(position)});
                            //db.delete("ITEMDATA", "CAT = ?", new String[]{cattitles.get(position)});
                            cattitles.remove(position);
                            catPhotos.remove(position);
                            Toast toast = Toast.makeText(ct, "Category deleted.", Toast.LENGTH_SHORT);
                            toast.show();

                            //db.close();
                        }

                        //((HomeScreen) ct).onResume();

                    }
                });
                build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                build.show();
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return cattitles.size();
    }
}
