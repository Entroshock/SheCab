package com.example.shecab;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaxiOptionsActivity extends AppCompatActivity {

    private Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_options);

        Button buttonEco = findViewById(R.id.buttonEcoTaxi);
        Button buttonNormal = findViewById(R.id.buttonNormalTaxi);
        buttonConfirm = findViewById(R.id.buttonConfirmBooking);

        buttonEco.setOnClickListener(v -> enableConfirmButton("Eco-Friendly Taxi"));
        buttonNormal.setOnClickListener(v -> enableConfirmButton("Normal Taxi"));
        buttonConfirm.setOnClickListener(v -> confirmBooking());
    }

    private void enableConfirmButton(String taxiType) {
        buttonConfirm.setEnabled(true);
        buttonConfirm.setTag(taxiType); // Storing the taxi type choice in the button's tag
    }

    private void confirmBooking() {
        String taxiType = (String) buttonConfirm.getTag();
        // Perform the booking process
        Toast.makeText(this, "Booking confirmed for a " + taxiType, Toast.LENGTH_LONG).show();
        // Optionally navigate to another activity or show a dialog
        Intent intent = new Intent(this, BookingConfirmationActivity.class);
        startActivity(intent);

    }
}
