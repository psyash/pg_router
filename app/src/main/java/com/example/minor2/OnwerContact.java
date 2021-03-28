package com.example.minor2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OnwerContact extends AppCompatActivity {


    TextView t1,t2;

    String ownername;
    String ownerphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onwer_contact);
        t1 = findViewById(R.id.ownername);
        t2 = findViewById(R.id.ownerphone);
        ownername = getIntent().getStringExtra("pgowner");
        ownerphone = getIntent().getStringExtra("pgphone");

    }
}
