package com.example.minor2;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class UserHomePage extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter adapter;
    Button b1;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference("PG's/");
        b1 = (Button)findViewById(R.id.logout_user);
        fetch();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toasty.success(getApplicationContext(),"Logged Out",Toasty.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(UserHomePage.this,MainActivity.class));
            }
        });

    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("PG's/");

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, new SnapshotParser<Model>() {
                            @NonNull
                            @Override
                            public Model parseSnapshot(@NonNull DataSnapshot snapshot) {
                                pgname = snapshot.child("pgname").getValue().toString();
                                norooms = snapshot.child("norooms").getValue().toString();
                                sizeroom =snapshot.child("sizeroom").getValue().toString();
                                internet =snapshot.child("internet").getValue().toString();
                                laundary = snapshot.child("laundary").getValue().toString();
                                maid = snapshot.child("maid").getValue().toString();
                                otherfacilities = snapshot.child("otherfacilities").getValue().toString();
                                rent = snapshot.child("rent").getValue().toString();
                                solar =  snapshot.child("solar").getValue().toString();
                                backup = snapshot.child("backup").getValue().toString();
                                boys = snapshot.child("boys").getValue().toString();
                                girls = snapshot.child("girls").getValue().toString();
                                Latitude = snapshot.child("Latitude").getValue().toString();
                                Longitude = snapshot.child("Longitude").getValue().toString();
                                P1imageURL = snapshot.child("P1imageURL").getValue().toString();
                                P2imageURL = snapshot.child("P2imageURL").getValue().toString();
                                P3imageURL = snapshot.child("P3imageURL").getValue().toString();


                                return new Model(snapshot.child("pgname").getValue().toString(),
                                        snapshot.child("norooms").getValue().toString(),
                                        snapshot.child("sizeroom").getValue().toString(),
                                        snapshot.child("internet").getValue().toString(),
                                        snapshot.child("laundary").getValue().toString(),
                                        snapshot.child("maid").getValue().toString(),
                                        snapshot.child("otherfacilities").getValue().toString(),
                                        snapshot.child("rent").getValue().toString(),
                                        snapshot.child("solar").getValue().toString(),
                                        snapshot.child("backup").getValue().toString(),
                                        snapshot.child("boys").getValue().toString(),
                                        snapshot.child("girls").getValue().toString(),
                                        snapshot.child("Latitude").getValue().toString(),
                                        snapshot.child("Longitude").getValue().toString(),
                                        snapshot.child("P1imageURL").getValue().toString(),
                                        snapshot.child("P2imageURL").getValue().toString(),
                                        snapshot.child("P3imageURL").getValue().toString()
                                        );
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Model, AdaptClass>(options) {
            @Override
            public AdaptClass onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.pg_details_row, parent, false);


                return new AdaptClass(view, getApplicationContext());
            }

            protected void onBindViewHolder(AdaptClass holder, final int position, Model model) {

                holder.setImageView(model.getP1imageURL());
                holder.setPgname1(model.getPgname());
                holder.setPgprice1(model.getRent());
                holder.setPgtype1(model.getType());


                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserHomePage.this,PGMainPage.class);
                        Log.d("pgname222",pgname);
                        i.putExtra("pgname",pgname);
                        i.putExtra("norooms",norooms);
                        i.putExtra("pgsize",sizeroom);
                        i.putExtra("internt",internet);
                        i.putExtra("laundary",laundary);
                        i.putExtra("maid",maid);
                        i.putExtra("other",otherfacilities);
                        i.putExtra("rent",rent);
                        i.putExtra("solar",solar);
                        i.putExtra("backup",backup);
                        i.putExtra("boys",boys);
                        i.putExtra("girls",girls);
                        i.putExtra("Latitude",Latitude);
                        i.putExtra("Longitude",Longitude);
                        i.putExtra("P1",P1imageURL);
                        i.putExtra("p2",P2imageURL);
                        i.putExtra("P3",P3imageURL);
                        startActivity(i);

                    }
                });
            }

        };
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

}
