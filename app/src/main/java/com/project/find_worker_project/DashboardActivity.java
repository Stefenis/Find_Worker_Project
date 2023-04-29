package com.project.find_worker_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView nav;
    LinearLayout layoutPlumber;
    LinearLayout layoutElectrician;
    LinearLayout layoutLaptop;
    LinearLayout layoutACRepairing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView nav = findViewById(R.id.nav);

        nav.setSelectedItemId(R.id.home);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;



                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });

        layoutPlumber = findViewById(R.id.layoutPlumber);

        layoutPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, PlumberActivity.class);
                startActivity(intent);
            }
        });

        layoutElectrician = findViewById(R.id.layoutElectrician);

        layoutElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ElectricianActivity.class);
                startActivity(intent);
            }
        });



        layoutLaptop = findViewById(R.id.layoutLaptoprepairing);

        layoutLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, LaptopRepairingActivity.class);
                startActivity(intent);
            }
        });

        layoutACRepairing = findViewById(R.id.layoutACRepairing);

        layoutACRepairing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ACRepairingActivity.class);
                startActivity(intent);
            }
        });
    }
}