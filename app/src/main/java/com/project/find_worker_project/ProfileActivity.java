        package com.project.find_worker_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

        public class ProfileActivity extends AppCompatActivity {


        BottomNavigationView nav;
        LinearLayout logoutButton;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_profile);

            BottomNavigationView nav = findViewById(R.id.nav);

            nav.setSelectedItemId(R.id.profile);


            nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){

                        case R.id.home:
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                            overridePendingTransition(0,0);
                            return true;

                        case R.id.search:
                            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                            overridePendingTransition(0,0);
                            return true;


                        case R.id.profile:

                            return true;
                    }
                    return true;
                }
            });

            logoutButton = findViewById(R.id.linearlayout_logout);

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Clear user session data
                    // ...

                    // Redirect user to login screen
                    Toast.makeText(ProfileActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });


    }
}