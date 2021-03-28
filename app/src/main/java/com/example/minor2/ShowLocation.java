package com.example.minor2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double Latitude;
    private double Longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location2);
        Latitude = Double.parseDouble(getIntent().getStringExtra("Latitude"));
        Longitude = Double.parseDouble(getIntent().getStringExtra("Longitude"));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(Latitude, Longitude);
        mMap.addMarker(new MarkerOptions().position(location).title("PG Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

    }
}
