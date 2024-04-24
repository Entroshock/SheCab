package com.example.shecab;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class DriverCreateAccountActivity extends AppCompatActivity {

    EditText driverEmail, driverPassword, driverPhone;
    ImageView driverImage;
    TextView loginTextView;
    Button driverRegister;

    private Uri selectedImageUri;

    private ActivityResultLauncher<String> imagePickerLauncher;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_create_account);

        // Initialize ActivityResultLauncher
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    selectedImageUri = result;
                    driverImage.setImageURI(selectedImageUri);
                }
            }
        });



        driverEmail = findViewById(R.id.driveEmail);
        driverPassword = findViewById(R.id.drivePassword);
        driverImage = findViewById(R.id.driverImage);
        driverPhone = findViewById(R.id.drivePhone);
        driverRegister = findViewById(R.id.driveRegister);
        loginTextView = findViewById(R.id.textView5);

        driverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select image
                imagePickerLauncher.launch("image/*");
            }
        });

        driverRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Driver
                DriverEntity driverEntity = new DriverEntity();
                driverEntity.setDriverEmail(driverEmail.getText().toString());
                driverEntity.setDriverPassword(driverPassword.getText().toString());
                driverEntity.setDriverPhone(driverPhone.getText().toString());

                if (selectedImageUri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] imageData = baos.toByteArray();
                        driverEntity.setPhotoID(imageData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


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
//                                   startActivity(intent);
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
                driverEntity.getDriverPhone().isEmpty() ||
                driverEntity.getPhotoID() == null || driverEntity.getPhotoID().length == 0)

        {
            return false;
        }
        return true;

    }
}



