
package com.example.shecab;

import com.google.android.gms.maps.model.LatLng;

public class MockPassenger {
    String name;
    String destination;
    LatLng location;

    public MockPassenger(String name, String destination, LatLng location) {
        this.name = name;
        this.destination = destination;
        this.location = location;
    }
}