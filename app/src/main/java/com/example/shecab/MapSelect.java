package com.example.shecab;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapSelect extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        // Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); // Make sure you have a map fragment in your XML with this ID
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapSelect.this, MapGoogle.class);
            startActivity(intent);
        });

        Button confirmButton = findViewById(R.id.confirmButton);
        boolean isBookingConfirmed = getIntent().getBooleanExtra("bookingConfirmed", false);
        if (isBookingConfirmed) {
            confirmButton.setVisibility(View.GONE); // Hide confirm button if booking was confirmed
        } else {
            confirmButton.setVisibility(View.VISIBLE);
            confirmButton.setOnClickListener(v -> {
                Intent intent = new Intent(MapSelect.this, MapGoogle.class);
                EditText pickupEditText = findViewById(R.id.pickUpLocationEditText);
                EditText destinationEditText = findViewById(R.id.destinationEditText);
                intent.putExtra("pickup_location", pickupEditText.getText().toString());
                intent.putExtra("destination_location", destinationEditText.getText().toString());
                startActivity(intent);
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        enableUserLocation(); // Calls method to enable location if permission granted
    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (gMap != null) {
                gMap.setMyLocationEnabled(true);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1); // You can use a constant for the request code
        }
    }
}
