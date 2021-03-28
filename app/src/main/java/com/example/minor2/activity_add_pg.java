package com.example.minor2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class activity_add_pg extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.minor2.EXTRA_TEXT";
    public static final String EXTRA_TEXT_1 = "com.example.minor2.EXTRA_TEXT_1";
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    EditText e1,e2,e3,e4,e5;
    CheckBox c1,c2,c3,c4,c5;
    String internet = "None";
    String backup = "None";
    String solar = "None";
    String laundary = "None";
    String maid = "None";
    String male = "None";
    String female = "None";
    String pgowner = "Rajeshhhhh";
    String pgphone = "None";
    Button b1,b2,b3;
    RadioGroup rg;
    RadioButton rbgirls,rbboys;
    int selectid;
    String str;
    SharedPreferences sp;
    static int locationAdded = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__pg);
        ActivityCompat.requestPermissions(activity_add_pg.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        e1 = (EditText)findViewById(R.id.pgname);
        e2 = (EditText)findViewById(R.id.norooms);
        e3 = (EditText)findViewById(R.id.sizeroom);
        e4 = (EditText)findViewById(R.id.otherfacilties);
        e5 = (EditText)findViewById(R.id.rent);
        c1 = (CheckBox)findViewById(R.id.internet);
        c2 = (CheckBox)findViewById(R.id.backup);
        c3 = (CheckBox)findViewById(R.id.solar);
        c4 = (CheckBox)findViewById(R.id.laundary);
        c5 = (CheckBox)findViewById(R.id.maid);
        b1 = (Button)findViewById(R.id.submit);
        b2 = (Button)findViewById(R.id.add_location);
        b3 = (Button)findViewById(R.id.add_photo);
        rg = (RadioGroup)findViewById(R.id.radioGroup);
        rbboys = (RadioButton)findViewById(R.id.boys);
        rbgirls = (RadioButton)findViewById(R.id.girls);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = mDatabase.getReference("Owners/"+mAuth.getUid());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pgname1 = e1.getText().toString();
                final String norooms1 = e2.getText().toString();
                final String sizerooms1 = e3.getText().toString();
                final String other1 = e4.getText().toString();
                final String rent1 = e5.getText().toString();
                if (pgname1.isEmpty() || norooms1.isEmpty() || sizerooms1.isEmpty() || rent1.isEmpty() || other1.isEmpty()){
                    Toasty.error(getApplicationContext(),"Enter Missing Details",Toasty.LENGTH_SHORT).show();
                }
                else if (!rbgirls.isChecked() && !rbboys.isChecked()){
                    Toasty.error(getApplicationContext(),"Select a PG Type",Toasty.LENGTH_SHORT).show();
                }
                else if (locationAdded==0){
                    Toasty.error(getApplicationContext(),"Location not Added",Toasty.LENGTH_SHORT).show();
                }
                else if (locationAdded==1){
                    Toasty.error(getApplicationContext(),"Photos not Uploaded",Toasty.LENGTH_SHORT).show();
                }
                else{
                    if (c1.isChecked()){
                        internet = "Yes";
                    }
                    if (c2.isChecked()){
                        backup = "Yes";
                    }
                    if (c3.isChecked()){
                        solar = "Yes";
                    }
                    if (c4.isChecked()){
                        laundary = "Yes";
                    }
                    if (c5.isChecked()){
                        maid = "Yes";
                    }
                    if (rbgirls.isChecked()){
                        female = "Girls";
                    }
                    if (rbboys.isChecked()){
                        male = "Boys";
                    }
                    try{
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = firebaseDatabase.getReference("Owners/"+mAuth.getUid());
                        DatabaseReference myRef2 = firebaseDatabase.getReference("PG's");
                        DatabaseReference myRef3 = myRef2.child(pgname1);
                        DatabaseReference myRef1 = myRef.child("PG's").child(pgname1);

                       // Model pgDetails = new Model(pgname1,norooms1,sizerooms1,internet,laundary,maid,other1,rent1,solar,backup,male,female);
                        myRef1.child("pgname").setValue(pgname1);
                        myRef1.child("norooms").setValue(norooms1);
                        myRef1.child("sizeroom").setValue(sizerooms1);
                        myRef1.child("otherfacilities").setValue(other1);
                        myRef1.child("rent").setValue(rent1);
                        myRef1.child("internet").setValue(internet);
                        myRef1.child("backup").setValue(backup);
                        myRef1.child("solar").setValue(solar);
                        myRef1.child("maid").setValue(maid);
                        myRef1.child("laundary").setValue(laundary);
                        myRef1.child("boys").setValue(male);
                        myRef1.child("girls").setValue(female);

                        myRef3.child("pgname").setValue(pgname1);
                        myRef3.child("norooms").setValue(norooms1);
                        myRef3.child("sizeroom").setValue(sizerooms1);
                        myRef3.child("otherfacilities").setValue(other1);
                        myRef3.child("rent").setValue(rent1);
                        myRef3.child("internet").setValue(internet);
                        myRef3.child("backup").setValue(backup);
                        myRef3.child("solar").setValue(solar);
                        myRef3.child("maid").setValue(maid);
                        myRef3.child("laundary").setValue(laundary);
                        myRef3.child("boys").setValue(male);
                        myRef3.child("girls").setValue(female);
                        myRef3.child("pgowner").setValue(pgowner);
                        myRef3.child("pgphone").setValue(pgphone);
                        Toasty.success(getApplicationContext(),"Data Saved Successfully",Toasty.LENGTH_SHORT).show();


                    }
                    catch (NullPointerException e){
                        Log.d("Errors",e.getMessage());
                    }
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pgname = e1.getText().toString();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference("Owners/"+mAuth.getUid());
                DatabaseReference myRef1 = myRef.child("PG's").child(pgname);
                DatabaseReference myRef2 = firebaseDatabase.getReference("PG's");
                DatabaseReference myRef3 = myRef2.child(pgname);
                GEOTracker geoTracker = new GEOTracker(getApplicationContext());
                Location l = geoTracker.getLocation();
                if (l!=null){
                    double latitude = l.getLatitude();
                    double longitude = l.getLongitude();
                    LatLng latLng = new LatLng(latitude,longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try{
                        List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
                        str = addresses.get(0).getLocality()+" ";
                        str += addresses.get(0).getCountryName();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    myRef1.child("Latitude").setValue(latitude+"");
                    myRef1.child("Longitude").setValue(longitude+"");
                    myRef3.child("Latitude").setValue(latitude+"");
                    myRef3.child("Longitude").setValue(longitude+"");
                    locationAdded = 1;
                    Toasty.info(getApplicationContext(),"Location Added!!",Toast.LENGTH_SHORT).show();
                }
            }

        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationAdded = 2;
                String pgname = e1.getText().toString();
                Intent i = new Intent(activity_add_pg.this,OwnerAddPhotos.class);
                i.putExtra(EXTRA_TEXT,pgname);
                startActivity(new Intent(activity_add_pg.this,OwnerAddPhotos.class));
            }
        });


    }
}
