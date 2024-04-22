package com.example.shecab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signUpButton = findViewById(R.id.buttontwo);
        Button driverLoginButton = findViewById(R.id.driverLoginButton);
        Button userLoginButton = findViewById(R.id.userLoginButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the CreateAccountActivity
                Intent intent = new Intent(MainActivity.this, optionscreen.class);
                startActivity(intent);
            }
        });

        userLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the CreateAccountActivity
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        driverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DriverLogin.class);
                startActivity(intent);
            }
        });




    }
}