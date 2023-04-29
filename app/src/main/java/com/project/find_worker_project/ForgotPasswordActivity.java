package com.project.find_worker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText input_email;
    Button reset_btn;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        input_email = findViewById(R.id.inputEmail);
        reset_btn = findViewById(R.id.reset_btn);
        databaseHelper = new DatabaseHelper(this);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = input_email.getText().toString();
                Boolean checkemail = databaseHelper.checkEmail(email);
                if (checkemail==true){
                    Intent intent = new Intent(getApplicationContext(),ResetActivity.class);
                    intent.putExtra("inputemail",email);
                    startActivity(intent);
                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "Email does not exists", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}