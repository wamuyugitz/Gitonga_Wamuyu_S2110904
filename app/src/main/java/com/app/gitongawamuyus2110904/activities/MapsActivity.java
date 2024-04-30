/*
 * Author: Wamuyu Gitonga
 * Student ID: s2110904
 */
package com.app.gitongawamuyus2110904.activities;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.app.gitongawamuyus2110904.R;
import com.app.gitongawamuyus2110904.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView detail_back_Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Find the ImageView by ID
        detail_back_Map = findViewById(R.id.detail_back_Map);

        // Set a click listener for the ImageView
        detail_back_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainActivity
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Find the SupportMapFragment by ID
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);

        // Check if the found fragment is an instance of SupportMapFragment
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e("MapsActivity", "Failed to find map fragment by ID.");
            // Optionally, display an error to the user
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set the initial camera position to a specific location with a 200 km radius
        LatLng initialLocation = new LatLng(37.0902, -95.7129);
        float zoomLevel = getZoomLevelForRadius(200000); // 200 km in meters
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, zoomLevel));
        // Add markers for specific locations with their names
        addMarker("Bangladesh", 23.6850, 90.3563);
        addMarker("Glasgow", 55.8642, -4.2518);
        addMarker("London", 51.5074, -0.1278);
        addMarker("New York", 40.7128, -74.0060);
        addMarker("Oman", 21.4735, 55.9754);
        addMarker("Mauritius", -20.3484, 57.5522);

        Log.d("XCV","Marker got here");

        addMarker("USA", 37.0902, -95.7129);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Open detail activity when a marker is clicked
                String markerTitle = marker.getTitle();
                Intent intent = new Intent(MapsActivity.this, ThreeDaysForcast.class);
                intent.putExtra("UNIVERSITY_LOCATION", markerTitle);
                startActivity(intent);
                return true;
            }
        });
    }

    // Method to add a marker with specified name and coordinates
    private void addMarker(String name, double latitude, double longitude) {
        LatLng location = new LatLng(latitude, longitude);
        Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
        if (name.equals("America")) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10));
        }
    }

    // Helper method to calculate zoom level for a given radius
    private float getZoomLevelForRadius(double radius) {
        // The zoom level required to show the specified radius on the map
        double scale = radius / 500; // 500 meters as reference
        return (float) (16 - Math.log(scale) / Math.log(2));
    }
}