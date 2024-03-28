package com.example.shecab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shecab.R;

public class CreateAccountActivity extends AppCompatActivity {
    EditText userEmail, userPassword, userPhone;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);

        userPhone = findViewById(R.id.userPhone);
        register = findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create User
                UserEntity userEntity = new UserEntity();
                userEntity.setUserEmail(userEmail.getText().toString());
                userEntity.setUserPassword(userPassword.getText().toString());
                userEntity.setUserPhone(userPhone.getText().toString());

                if (validateInput(userEntity)){
                    // Do insert Operation
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Register User
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User Registered!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
                else {
                    Toast.makeText(getApplicationContext(), "fill all fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private Boolean validateInput(UserEntity userEntity) {
        if (userEntity.getUserEmail().isEmpty() ||
            userEntity.getUserPassword().isEmpty() ||
            userEntity.getUserPhone().isEmpty())
        {
            return false;
        }
        return true;

    }
}