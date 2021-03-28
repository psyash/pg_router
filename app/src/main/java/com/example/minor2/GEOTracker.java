package com.example.minor2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

public class GEOTracker implements LocationListener {
    Context c;
    public GEOTracker(Context context){
        this.c = context;
    }

    public Location getLocation(){
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(c,"Permission not granted",Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager locationManager = (LocationManager)c.getSystemService(LOCATION_SERVICE);
        boolean isGPSenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSenabled){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
            Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        }
        else {
            Toast.makeText(c,"Enable GPS",Toast.LENGTH_SHORT).show();
        }

        return null;

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
