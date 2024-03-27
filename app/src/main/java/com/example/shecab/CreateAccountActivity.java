package com.example.shecab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shecab.R;

public class CreateAccountActivity extends AppCompatActivity {
    EditText userEmail, userPassword, userConfirmPassword, userPhone;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        userConfirmPassword = findViewById(R.id.userConfirmPassword);
        userPhone = findViewById(R.id.userPhone);
        register = findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create User
                UserEntity userEntity = new UserEntity();
                userEntity.setEmail(userEmail.getText().toString());
                userEntity.setPassword(userPassword.getText().toString());
                userEntity.setPhone(userPhone.getText().toString());
            }
        });

    }
}