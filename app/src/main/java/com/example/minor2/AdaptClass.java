package com.example.minor2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptClass extends RecyclerView.ViewHolder {

    public ConstraintLayout root;
    public ImageView imageView;
    public TextView pgname1;
    public TextView pgprice1;
    public TextView pgtype1;
    Context context;
    View view;



    public AdaptClass(View itemView,Context context) {
        super(itemView);
        view = itemView;
        this.context = context;
        root = itemView.findViewById(R.id.list_root);
        imageView = (ImageView)itemView.findViewById(R.id.pg_photo);
         pgname1 = (TextView)itemView.findViewById(R.id.pg_name);
         pgprice1 = (TextView)itemView.findViewById(R.id.pg_price);
         pgtype1 = (TextView)itemView.findViewById(R.id.pg_type);
    }


    public void setImageView(String image) {
        Picasso.with(context).load(image).fit().centerCrop().into(imageView);
    }

    public void setPgname1(String pgname) {
        pgname1.setText("PG Name: "+pgname);
    }

    public void setPgprice1(String pgprice) {
        pgprice1.setText("Price: ₹"+pgprice);
    }

    public void setPgtype1(String pgtype) {
        pgtype1.setText("For: "+pgtype);
    }



}
