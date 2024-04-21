package com.example.shecab;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static List<MockPassenger> createMockPassengers() {
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
}

