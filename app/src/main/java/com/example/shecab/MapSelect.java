package com.example.shecab;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.MapView;

public class MapSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapSelect.this, MapGoogle.class);
                startActivity(intent);
            }
        });

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapSelect.this, MapGoogle.class);
                EditText pickupEditText = findViewById(R.id.pickUpLocationEditText);
                EditText destinationEditText = findViewById(R.id.destinationEditText);
                intent.putExtra("pickup_location", pickupEditText.getText().toString());
                intent.putExtra("destination_location", destinationEditText.getText().toString());
                startActivity(intent);
            }
        });

    }
}
