package com.cs307.evant.evant;


        import android.content.Context;
        import android.content.DialogInterface;
        import android.graphics.Bitmap;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.PopupMenu;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

/**
 * Created by avi12 on 2/22/2018.
 */

public class catAdapter extends RecyclerView.Adapter<catAdapter.MyViewHolder>{

    private ArrayList<Bitmap> catPhotos;
    private ArrayList<String> cattitles;
    private Context ct;
    //private ImageView iv;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView iv;
        //public TextView cnts;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //cnts = (TextView) view.findViewById(R.id.count);
            iv = (ImageView) view.findViewById(R.id.photo);


        }
    }


    public catAdapter( ArrayList <String> b)
    {
        //catPhotos = a;
        cattitles = b;
    }

    @Override
    public catAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ct = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cat_card, parent, false);

        return new catAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(catAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(cattitles.get(position));
        //Drawable myDrawable = getApplicationContext().getResources().getDrawable(R.drawable.shoebackground);
        //String fpath = photopath.get(position);
        holder.iv.setImageBitmap(catPhotos.get(position));
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return cattitles.size();
    }
}
