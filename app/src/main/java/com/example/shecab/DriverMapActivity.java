package com.example.shecab;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class DriverMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Marker currentLocationMarker;
    private LatLng currentPassengerLocation;
    private TextView passengerNameTextView;
    private TextView passengerDestinationTextView;
    private TextView tripPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivermap);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        passengerNameTextView = findViewById(R.id.passengerNameTextView);
        passengerDestinationTextView = findViewById(R.id.passengerDestinationTextView);

        // initialize price TextView and Button
        tripPriceTextView = findViewById(R.id.tripPriceTextView);
        tripPriceTextView.setText("Trip Price: $10");

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(v -> {
            Toast.makeText(this, "Accepted ride to " + passengerDestinationTextView.getText(), Toast.LENGTH_SHORT).show();
            navigateToPassenger(currentPassengerLocation);
        });

        // set OnClickListener for the completeTripButton
        Button completeTripButton = findViewById(R.id.completeTripButton);
        completeTripButton.setOnClickListener(v -> completeTrip());
    }

    private void completeTrip() {
        // display a simple toast message when the trip is completed
        Toast.makeText(this, "Trip completed. Displaying fare.", Toast.LENGTH_SHORT).show();

        // display the trip price
        tripPriceTextView.setText("Trip Price: $10");
        tripPriceTextView.setVisibility(View.VISIBLE); // make the price visible
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        enableMyLocation();

        addMockPassengers();

        mMap.setOnMarkerClickListener(marker -> {
            showRouteToPassenger(marker);
            return true;
        });
    }

    private void showRouteToPassenger(Marker passengerMarker) {
        if (currentLocationMarker == null) {
            Toast.makeText(this, "Current location not available", Toast.LENGTH_SHORT).show();
            return;
        }

        mMap.clear();

        passengerMarker = mMap.addMarker(new MarkerOptions().position(passengerMarker.getPosition()).title(passengerMarker.getTitle()));

        PolylineOptions options = new PolylineOptions()
                .add(currentLocationMarker.getPosition())
                .add(passengerMarker.getPosition());

        mMap.addPolyline(options);

        passengerNameTextView.setText(passengerMarker.getTitle());
        passengerDestinationTextView.setText(passengerMarker.getSnippet());

        findViewById(R.id.relativeLayoutContainingTheTextViews).setVisibility(View.VISIBLE);

        currentPassengerLocation = passengerMarker.getPosition();

        showAcceptRouteDialog(passengerMarker);
    }

    private void showAcceptRouteDialog(Marker passengerMarker) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Route")
                .setMessage("Do you want to navigate to " + passengerMarker.getTitle() + "?")
                .setPositiveButton("Accept", (dialog, which) -> {
                    navigateToPassenger(passengerMarker.getPosition());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void navigateToPassenger(LatLng passengerLocation) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(passengerLocation, 15), 3000, null);
        Toast.makeText(this, "Navigating to passenger...", Toast.LENGTH_SHORT).show();
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            updateDriverLocation();
        }
    }

    private void updateDriverLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null && mMap != null) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                if (currentLocationMarker != null) {
                    currentLocationMarker.setPosition(currentLocation);
                } else {
                    currentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Your Location"));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            } else {
                Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMockPassengers() {
        List<MockPassenger> passengers = createMockPassengers();
        for (MockPassenger passenger : passengers) {
            mMap.addMarker(new MarkerOptions()
                    .position(passenger.location)
                    .title(passenger.name)
                    .snippet("Destination: " + passenger.destination));
        }
    }
    //Mock Passengers location
    private List<MockPassenger> createMockPassengers() {
        List<MockPassenger> mockPassengers = new ArrayList<>();
        mockPassengers.add(new MockPassenger("Alice", "Museum of Art", new LatLng(34.0522, -118.2437)));
        mockPassengers.add(new MockPassenger("Bob", "City Library", new LatLng(34.052235, -118.243683)));
        mockPassengers.add(new MockPassenger("Charlie", "Central Park", new LatLng(34.052250, -118.243690)));
        mockPassengers.add(new MockPassenger("Dana", "Sports Arena", new LatLng(34.052265, -118.243695)));
        mockPassengers.add(new MockPassenger("Evan", "Tech Hub", new LatLng(34.052280, -118.243700)));
        mockPassengers.add(new MockPassenger("Luka", "Ljubljana Castle", new LatLng(46.051426, 14.505966)));
        mockPassengers.add(new MockPassenger("Maja", "Lake Bled", new LatLng(46.362274, 14.095289)));
        mockPassengers.add(new MockPassenger("Nik", "Postojna Cave", new LatLng(45.782520, 14.204619)));
        mockPassengers.add(new MockPassenger("Sara", "Piran", new LatLng(45.528319, 13.568289)));
        mockPassengers.add(new MockPassenger("Marko", "Maribor Cathedral", new LatLng(46.559422, 15.645881)));
        mockPassengers.add(new MockPassenger("Iza", "Tivoli Park", new LatLng(46.056946, 14.505751)));
        mockPassengers.add(new MockPassenger("Jan", "Ljubljana Zoo", new LatLng(46.052410, 14.472850)));
        mockPassengers.add(new MockPassenger("Ema", "Metelkova Art Center", new LatLng(46.056294, 14.518429)));
        mockPassengers.add(new MockPassenger("Urban", "Dragon Bridge", new LatLng(46.053342, 14.509339)));
        mockPassengers.add(new MockPassenger("Sara", "Ljubljana Castle", new LatLng(46.051426, 14.505966)));
        mockPassengers.add(new MockPassenger("Luka", "Central Market", new LatLng(46.051080, 14.508305)));
        mockPassengers.add(new MockPassenger("John", "Dublin Castle", new LatLng(53.3429, -6.2675)));
        mockPassengers.add(new MockPassenger("Fiona", "Guinness Storehouse", new LatLng(53.3419, -6.2867)));
        mockPassengers.add(new MockPassenger("Sean", "St Stephen's Green", new LatLng(53.3382, -6.2591)));
        mockPassengers.add(new MockPassenger("Ciara", "Temple Bar", new LatLng(53.3456, -6.2625)));
        mockPassengers.add(new MockPassenger("Liam", "Trinity College Dublin", new LatLng(53.3441, -6.2584)));
        mockPassengers.add(new MockPassenger("Eva", "Phoenix Park", new LatLng(53.3562, -6.3288)));
        mockPassengers.add(new MockPassenger("Nora", "Dublin Zoo", new LatLng(53.3538, -6.3053)));
        mockPassengers.add(new MockPassenger("Oscar", "The National Gallery of Ireland", new LatLng(53.3409, -6.2525)));
        mockPassengers.add(new MockPassenger("Molly", "The Spire", new LatLng(53.3498, -6.2603)));
        mockPassengers.add(new MockPassenger("Luke", "Docklands", new LatLng(53.3475, -6.2428)));
        return mockPassengers;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    // mock Passenger class
    public static class MockPassenger {
        String name;
        String destination;
        LatLng location;

        public MockPassenger(String name, String destination, LatLng location) {
            this.name = name;
            this.destination = destination;
            this.location = location;
        }
    }
}

