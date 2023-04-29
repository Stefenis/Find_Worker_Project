package com.project.find_worker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ElectricianActivity extends AppCompatActivity {

        Button bookingbtn;
        ImageView backButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_electrician);

            bookingbtn = findViewById(R.id.Booking_Button);
            backButton = findViewById(R.id.backbtn);

            bookingbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ElectricianActivity.this, AddDetailsActivity.class);
                    startActivity(intent);
                }
            });

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add your custom logic here
                    finish();
                }
            });


        }
    }