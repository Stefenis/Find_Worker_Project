package com.project.find_worker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class SelectOtherLocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LatLng mSelectedLatLng;
    private Button mSaveButton;
    private SQLiteDatabase mDatabase;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_other_location);

        searchView = findViewById(R.id.sv_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s){
                String location = searchView.getQuery().toString();
                List<Address> addressList=null;

                if (location!= null || !location.equals(""))
                {
                    Geocoder geocoder = new Geocoder((SelectOtherLocationActivity.this));
                    try {
                        addressList = geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                    mSelectedLatLng = latLng;
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s){
                return false;
            }
        });
        mapFragment.getMapAsync(this);

        mSaveButton = findViewById(R.id.saveLocation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(SelectOtherLocationActivity.this,
                    android.Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(SelectOtherLocationActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocation();
                makeNotification();
            }
        });

        // Open the SQLite database
        mDatabase = openOrCreateDatabase("otherlocation.db", MODE_PRIVATE, null);
        // Create the table if it doesn't exist
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS locations(id INTEGER PRIMARY KEY AUTOINCREMENT, latitude REAL, longitude REAL)");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mSelectedLatLng = latLng;
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
    }

    private void saveLocation() {
        if (mSelectedLatLng != null) {
            // Insert the location into the database
            ContentValues values = new ContentValues();
            values.put("latitude", mSelectedLatLng.latitude);
            values.put("longitude", mSelectedLatLng.longitude);
            long id = mDatabase.insert("locations", null, values);

            if (id > -1) {
                Toast.makeText(this, "Location shared successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectOtherLocationActivity.this, DashboardActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error sharing location to database", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please select a location on the map", Toast.LENGTH_SHORT).show();
        }
    }



    public void makeNotification(){
        String chanelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), chanelID);
        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("SERVICE CONFIRMATION")
                .setContentText("Your service is confirmed. Our service workers will get back to you as soon as possible.")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    notificationManager.getNotificationChannel(chanelID);
            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(chanelID,
                        "Some describtion", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0,builder.build());
    }




}