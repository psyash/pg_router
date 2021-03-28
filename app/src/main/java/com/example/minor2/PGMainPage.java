package com.example.minor2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PGMainPage extends AppCompatActivity {

    private String pgname;
    private String norooms;
    private String sizeroom;
    private String internet;
    private String laundary;
    private String maid;
    private String otherfacilities;
    private String rent;
    private String solar;
    private String backup;
    private String boys;
    private String girls;
    private String Latitude;
    private String Longitude;
    private String P1imageURL;
    private String P2imageURL;
    private String P3imageURL;

    String ownername;
    String ownerphone;

    TextView t1,t2,t3,t4,t5;
    CheckBox c1,c2,c3,c4,c5;
    RadioButton rb1,rg1;
    Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgmain_page);
        pgname = getIntent().getStringExtra("pgname");
        norooms = getIntent().getStringExtra("norooms");
        sizeroom = getIntent().getStringExtra("pgsize");
        internet = getIntent().getStringExtra("internt");
        laundary = getIntent().getStringExtra("laundary");
        maid = getIntent().getStringExtra("maid");
        otherfacilities = getIntent().getStringExtra("other");
        rent = getIntent().getStringExtra("rent");
        solar = getIntent().getStringExtra("solar");
        backup = getIntent().getStringExtra("backup");
        boys = getIntent().getStringExtra("boys");
        girls = getIntent().getStringExtra("girls");
        Latitude = getIntent().getStringExtra("Latitude");
        Longitude = getIntent().getStringExtra("Longitude");
        P1imageURL = getIntent().getStringExtra("P1");
        P2imageURL = getIntent().getStringExtra("p2");
        P3imageURL = getIntent().getStringExtra("P3");
        ownername = getIntent().getStringExtra("pgowner");
        ownerphone = getIntent().getStringExtra("pgphone");

         t1 = findViewById(R.id.pgname2);
         t2 = findViewById(R.id.rooms2);
         t3 = findViewById(R.id.roomsize2);
         t4 = findViewById(R.id.otherfacilties2);
         t5 = findViewById(R.id.rent2);
         c1 = findViewById(R.id.backup);
         c2 = findViewById(R.id.laundary);
         c3 = findViewById(R.id.internet);
         c4 = findViewById(R.id.maid);
         c5 = findViewById(R.id.solar);
         b1 = findViewById(R.id.see_location);
         b2 = findViewById(R.id.show_photo2);
         rg1 = findViewById(R.id.girls);
         rb1 = findViewById(R.id.boys);

         t1.setText("PG Name: "+pgname);
         t2.setText("No of Rooms: "+norooms);
         t3.setText("Size of Room: "+"("+sizeroom+")"+"sq ft");
         t4.setText(otherfacilities);
         t5.setText("Rent: ₹"+rent);
         if (!internet.equals("None")){
             c3.setChecked(true);
         }
        if (!solar.equals("None")){
            c5.setChecked(true);
        }
        if (!maid.equals("None")){
            c4.setChecked(true);
        }
        if (!backup.equals("None")){
            c1.setChecked(true);
        }
        if (!laundary.equals("None")){
            c2.setChecked(true);
        }
        if (!boys.equals("None")){
            rb1.setChecked(true);
        }
        if (!girls.equals("None")){
            rg1.setChecked(true);
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PGMainPage.this,ShowLocation.class);
                i.putExtra("Latitude",Latitude);
                i.putExtra("Longitude",Longitude);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PGMainPage.this,ShowPGPhotos.class);
                i.putExtra("P1",P1imageURL);
                i.putExtra("p2",P2imageURL);
                i.putExtra("P3",P3imageURL);
                startActivity(i);
            }
        });



    }
}
