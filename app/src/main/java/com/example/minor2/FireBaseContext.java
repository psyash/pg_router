package com.example.minor2;

import android.app.Application;

import com.firebase.client.Firebase;

public class FireBaseContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

    }
}
