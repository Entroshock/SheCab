package com.example.shecab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class loginOptionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginoptionlcreen);

        ImageView driver = findViewById(R.id.imageView3);
        ImageView passenger = findViewById(R.id.imageView4);

        driver.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(loginOptionScreen.this, DriverLogin.class));
            }

        });

        passenger.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(loginOptionScreen.this, Login.class));
            }

        });


    }
}
