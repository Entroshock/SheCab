package com.example.shecab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DriverLogin extends AppCompatActivity {

    EditText driverLoginEmail, driverLoginPassword;
    Button driverLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverlogin);
        driverLoginEmail = findViewById(R.id.driverLoginEmail);
        driverLoginPassword = findViewById(R.id.driverLoginPassword);
        driverLogin = findViewById(R.id.driverLogin);

        driverLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String driverEmailText = driverLoginEmail.getText().toString();
                String driverPasswordText = driverLoginPassword.getText().toString();

                if (driverEmailText.isEmpty() || driverPasswordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();

                } else {
                    //perform query
                    DriverDatabase driverDatabase = DriverDatabase.getDriverDatabase(getApplicationContext());
                    DriverDao driverDao = driverDatabase.driverDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DriverEntity driverEntity= driverDao.login(driverEmailText, driverPasswordText);
                            if (driverEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            } else {

                                startActivity(new Intent(
                                        DriverLogin.this, DriverHome.class));
                            }
                        }
                    }).start();;
                }
            }
        });
    }


}
