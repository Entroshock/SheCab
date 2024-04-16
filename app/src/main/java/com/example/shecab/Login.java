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


public class Login extends AppCompatActivity {
    EditText userLoginEmail, userLoginPassword;
    Button userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userLoginEmail = findViewById(R.id.userLoginEmail);
        userLoginPassword = findViewById(R.id.userLoginPassword);
        userLogin = findViewById(R.id.userLogin);

        userLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userEmailText = userLoginEmail.getText().toString();
                String userPasswordText = userLoginPassword.getText().toString();

                if (userEmailText.isEmpty() || userPasswordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();

                } else {
                    //perform query
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity= userDao.login(userEmailText, userPasswordText);
                            if (userEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                        
                                    }
                                });
                            } else {

                                startActivity(new Intent(
                                        Login.this, Home.class));
                            }
                        }
                    }).start();;
                }
            }
        });


    }
}