package com.example.shecab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class optionscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionscreen);

        ImageView driver = findViewById(R.id.imageView3);
        ImageView passenger = findViewById(R.id.imageView4);

        driver.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(optionscreen.this, DriverCreateAccountActivity.class));
            }

        });

        passenger.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(optionscreen.this, CreateAccountActivity.class));
            }

        });


    }
}