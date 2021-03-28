package com.example.minor2;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class ownerHomePage1 extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    TextView userName;
    FirebaseStorage firebaseStorage;
    CardView a1;
    ImageView i1;
    Button logout;
    static String ownername;
    static String ownerphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home_page1);
        a1 = (CardView)findViewById(R.id.addPG);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        logout = (Button)findViewById(R.id.logout);
        firebaseStorage = FirebaseStorage.getInstance();
        i1 = (ImageView)findViewById(R.id.Photo);
        userName = (TextView)findViewById(R.id.Username);
        DatabaseReference databaseReference = mDatabase.getReference("Owners/"+mAuth.getUid());
        StorageReference storageReference = firebaseStorage.getReference();


        storageReference.child(mAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               String url =  uri.getLastPathSegment().toString();
                Log.d("uri",url);
                Picasso.with(getApplicationContext()).load(uri).fit().centerCrop().into(i1);
                Log.d("uri","photo arrived");
            }

        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OwnerProfile ownerProfile = dataSnapshot.getValue(OwnerProfile.class);
                userName.setText(ownerProfile.getOname());
                ownername = ownerProfile.getOname();
                ownerphone = ownerProfile.getOmobile();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),activity_add_pg.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }


    private void setting() {
    }

    private void logout() {
        mAuth.signOut();
        Toasty.success(getApplicationContext(),"Logged Out",Toasty.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(ownerHomePage1.this,owner_login.class));
    }

    public void AddDetails(View v){
        Intent i = new Intent(ownerHomePage1.this,activity_add_pg.class);
        startActivity(i);
    }

    public void ViewDetails(View v){

    }



}
