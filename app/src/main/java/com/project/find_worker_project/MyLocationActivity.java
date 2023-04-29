package com.project.find_worker_project;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.find_worker_project.databinding.ActivityMyLocationBinding;

import java.util.List;

public class MyLocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityMyLocationBinding binding;
    private LatLng mSelectedLatLng;
    private SQLiteDatabase mDatabase;

    private Button mSaveButton;
    List<Address> listGeoCoder;

    private static final int LOCATION_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if(isLocationPermissionGranted()){
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


            mSaveButton = findViewById(R.id.saveLocation);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                if (ContextCompat.checkSelfPermission(MyLocationActivity.this,
                        android.Manifest.permission.POST_NOTIFICATIONS) !=
                        PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MyLocationActivity.this,
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
            mDatabase = openOrCreateDatabase("mylocation.db", MODE_PRIVATE, null);
            // Create the table if it doesn't exist
            mDatabase.execSQL("CREATE TABLE IF NOT EXISTS locations(id INTEGER PRIMARY KEY AUTOINCREMENT, latitude REAL, longitude REAL)");

        }else{
            requestLocationPermission();
        }

//        try{
//            listGeoCoder = new Geocoder(this).getFromLocationName("Batticaloa",1);
//        }
//        catch  (Exception e){
//            e.printStackTrace();
//        }
//
//
//        double longitude = listGeoCoder.get(0).getLongitude();       //get longitude
//        double latitude = listGeoCoder.get(0).getLatitude();         //get latitude
//        String countryName = listGeoCoder.get(0).getCountryName();   //get Country Name
//
//        Log.i("GOOGLE_MAP_TAG", "Address has longitude: " + String.valueOf(longitude) +
//                " Latitude : " + String.valueOf(latitude) + "Country name : " + String.valueOf(countryName));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);



        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                == getPackageManager().PERMISSION_GRANTED){
            UiSettings uiSettings = mMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);

        }


    }

    @Override
    public void onMapClick(LatLng latLng) {
        mSelectedLatLng = latLng;
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
    }

    //Check the location PERMISSION_GRANTED or not
    private boolean isLocationPermissionGranted(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            return  true;
        }
        else {
            return  false;
        }
    }
    //Request location permission
    private void requestLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_CODE);
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
                Intent intent = new Intent(MyLocationActivity.this, DashboardActivity.class);
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
                .setContentText("Your service is confirmed. Our service workers will get back to you as soon as possible. ")
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