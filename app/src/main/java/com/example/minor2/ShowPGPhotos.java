package com.example.minor2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ShowPGPhotos extends AppCompatActivity {

    ImageView i1,i2,i3;
    private String P1imageURL;
    private String P2imageURL;
    private String P3imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pgphotos);
        i1 = findViewById(R.id.image_1);
        i2  = findViewById(R.id.image_2);
        i3 = findViewById(R.id.image_3);
        P1imageURL = getIntent().getStringExtra("P1");
        P2imageURL = getIntent().getStringExtra("p2");
        P3imageURL = getIntent().getStringExtra("P3");
        Picasso.with(this).load(P1imageURL).fit().centerCrop().into(i1);
        Picasso.with(this).load(P2imageURL).fit().centerCrop().into(i2);
        Picasso.with(this).load(P3imageURL).fit().centerCrop().into(i3);
    }
}
