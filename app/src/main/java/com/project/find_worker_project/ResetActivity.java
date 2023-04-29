package com.project.find_worker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetActivity extends AppCompatActivity {

    TextView input_email;
    EditText pass,re_pass;
    Button form_btn;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        input_email = findViewById(R.id.email_reset_text);
        pass = findViewById(R.id.password_reset);
        re_pass = findViewById(R.id.repassword_reset);
        form_btn = findViewById(R.id.form_btn);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        input_email.setText(intent.getStringExtra("inputemail"));

        form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = input_email.getText().toString();
                String password = pass.getText().toString();
                String repass = re_pass.getText().toString();

                if (password.equals(repass)){



                Boolean check_pass_update = databaseHelper.updatepassword(email,password);
                if (check_pass_update == true){
                    Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent1);
                    Toast.makeText(ResetActivity.this, "password updated successfully...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ResetActivity.this, "password not updated !!", Toast.LENGTH_SHORT).show();
                }
            }
                else  {
                    Toast.makeText(ResetActivity.this, "password not matched...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}