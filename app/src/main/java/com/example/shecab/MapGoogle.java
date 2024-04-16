package com.example.shecab;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;

public class MapGoogle extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    private LatLng initialFocusLatLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get the intent that started this activity
        Intent intent = getIntent();
        String pickupLocation = intent.getStringExtra("pickup_location");
        String destinationLocation = intent.getStringExtra("destination_location");

        // Assuming a method getDirections which handles the complete flow
        if (pickupLocation != null && destinationLocation != null) {
            getDirections(pickupLocation, destinationLocation);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        if (initialFocusLatLng != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialFocusLatLng, 15));
        }
    }

    private void getDirections(String originAddress, String destinationAddress) {
        geocodeAddress(originAddress, "Pickup Location", new GeocodeListener() {
            @Override
            public void onLocationDecoded(LatLng originLatLng, String originTitle) {
                if (gMap != null) {
                    gMap.addMarker(new MarkerOptions().position(originLatLng).title(originTitle));
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(originLatLng, 15)); // Optional: Adjust the zoom level here or after both markers are placed.
                }
                geocodeAddress(destinationAddress, "Destination", new GeocodeListener() {
                    @Override
                    public void onLocationDecoded(LatLng destinationLatLng, String destinationTitle) {
                        if (gMap != null) {
                            gMap.addMarker(new MarkerOptions().position(destinationLatLng).title(destinationTitle));
                            // Optional: Adjust camera to show both markers or fit the entire route when drawn.
                            fetchDirections(originLatLng, destinationLatLng);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("GeocodeError", "Error geocoding destination: " + error);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Log.e("GeocodeError", "Error geocoding origin: " + error);
            }
        });
    }


    private void geocodeAddress(String address, String title, GeocodeListener listener) {
        String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";
        String apiKey = "AIzaSyDRf4jd99D3ZtEmOaQQKXUjZ6DezfA4cD8"; // Securely store and use your API key
        String url = baseUrl + "?address=" + Uri.encode(address) + "&key=" + apiKey;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        double lat = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        double lng = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                        LatLng latLng = new LatLng(lat, lng);
                        listener.onLocationDecoded(latLng, title);
                    } catch (JSONException e) {
                        listener.onError("Failed to parse geocoding response: " + e.getMessage());
                    }
                },
                error -> listener.onError("Network error: " + error.toString())
        );

        queue.add(stringRequest);
    }


    private void fetchDirections(LatLng origin, LatLng destination) {
        String baseUrl = "https://maps.googleapis.com/maps/api/directions/json";
        String params = "origin=" + origin.latitude + "," + origin.longitude +
                "&destination=" + destination.latitude + "," + destination.longitude +
                "&key=" + "AIzaSyDRf4jd99D3ZtEmOaQQKXUjZ6DezfA4cD8";
        String url = baseUrl + "?" + params;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Parse response here
                        JSONObject json = new JSONObject(response);
                        String encodedPolyline = json.getJSONArray("routes")
                                .getJSONObject(0)
                                .getJSONObject("overview_polyline")
                                .getString("points");

                        // Draw polyline on the map
                        drawPolylineOnMap(encodedPolyline);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("DirectionsError", "Failed to fetch directions", error);
                });

        queue.add(request);
    }

    private void drawPolylineOnMap(String encodedPolyline) {
        List<LatLng> points = decodePolyline(encodedPolyline);
        if (!points.isEmpty()) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(points)
                    .width(12)
                    .color(Color.BLUE)
                    .geodesic(true);
            gMap.addPolyline(polylineOptions);

            // Optionally adjust the camera to fit the route in view
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng point : points) {
                builder.include(point);
            }
            gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100)); // padding around the edges
        }
    }


    private List<LatLng> decodePolyline(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng(((lat / 1E5)), ((lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }


    interface GeocodeListener {
        void onLocationDecoded(LatLng latLng, String title);
        void onError(String error);
    }


}
