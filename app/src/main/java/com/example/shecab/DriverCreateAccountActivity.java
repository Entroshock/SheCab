package com.example.shecab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DriverCreateAccountActivity extends AppCompatActivity {

    EditText driverEmail, driverPassword, driverPhone;
    TextView loginTextView;
    Button driverRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_create_account);

        driverEmail = findViewById(R.id.driveEmail);
        driverPassword = findViewById(R.id.drivePassword);

        driverPhone = findViewById(R.id.drivePhone);
        driverRegister = findViewById(R.id.driveRegister);
        loginTextView = findViewById(R.id.textView5);

        driverRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Driver
                DriverEntity driverEntity = new DriverEntity();
                driverEntity.setDriverEmail(driverEmail.getText().toString());
                driverEntity.setDriverPassword(driverPassword.getText().toString());
                driverEntity.setDriverPhone(driverPhone.getText().toString());

                if (validateDriverInput(driverEntity)){
                    // Do insert Operation
                    DriverDatabase driverDatabase = DriverDatabase.getDriverDatabase(getApplicationContext());
                    DriverDao driverDao = driverDatabase.driverDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Register User
                            driverDao.registerDriver(driverEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Driver Registered!", Toast.LENGTH_SHORT).show();
                                    //redirect to driversmap page
//                                    Intent intent = new Intent(DriverCreateAccountActivity.this, DriverMapActivity.class);
//                                    startActivity(intent);
                                }
                            });
                        }
                    }).start();

                    startActivity(new Intent(
                            DriverCreateAccountActivity.this, DriverLogin.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "fill all fields!", Toast.LENGTH_SHORT).show();
                }



            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DriverCreateAccountActivity.this, DriverLogin.class));
            }
        });
    }




    private Boolean validateDriverInput(DriverEntity driverEntity) {
        if (driverEntity.getDriverEmail().isEmpty() ||
                driverEntity.getDriverPassword().isEmpty() ||
                driverEntity.getDriverPhone().isEmpty())
        {
            return false;
        }
        return true;

    }
}



