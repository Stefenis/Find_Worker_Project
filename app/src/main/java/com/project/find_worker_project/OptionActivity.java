package com.project.find_worker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class OptionActivity extends AppCompatActivity {

    LinearLayout mylocation;
    LinearLayout otherLocation;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        mylocation = findViewById(R.id.btnMyLocation);
        otherLocation = findViewById(R.id.btnOtherLocation);
        backButton = findViewById(R.id.backbtn);

        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionActivity.this, MyLocationActivity.class);
                startActivity(intent);
            }
        });

        otherLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionActivity.this, SelectOtherLocationActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}