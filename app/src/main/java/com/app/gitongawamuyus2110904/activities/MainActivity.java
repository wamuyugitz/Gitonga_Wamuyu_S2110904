/*
 * Author: Wamuyu Gitonga
 * Student ID: s2110904
 */
package com.app.gitongawamuyus2110904.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.app.gitongawamuyus2110904.Adapters.UnivesitiesListAdapter;
import com.app.gitongawamuyus2110904.Models.UniListModel;
import com.app.gitongawamuyus2110904.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView; // RecyclerView to display the list of universities
    FloatingActionButton floatingActionButton; // FloatingActionButton for adding new universities
    UnivesitiesListAdapter adapter; // Adapter for populating the RecyclerView
    List<UniListModel> uniList; // List of university models

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isNetworkAvailable()) {
            setContentView(R.layout.activity_no_connection);
            // Setup try again button
            setupTryAgainButton();
        } else {
            setContentView(R.layout.activity_main);
            initializeViews();
            populateUniversityList();
            setupRecyclerView();
        }
    }

    // Method to initialize views
    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
    }

    // Method to create dummy data for the list of universities
    private void populateUniversityList() {
        uniList = new ArrayList<>();
        uniList.add(new UniListModel("GCU Campus", "USA"));
        uniList.add(new UniListModel("Glasgow Caledonian University", "Glasgow"));
        uniList.add(new UniListModel("GCU London", "London"));
        uniList.add(new UniListModel("GCNYC", "New York"));
        uniList.add(new UniListModel("Caledonian College Oman", "Oman"));
        uniList.add(new UniListModel("African Leadership Campus", "Mauritius"));
        uniList.add(new UniListModel("GCU Bangladesh", "Bangladesh"));
    }

    // Method to set up the RecyclerView with the adapter
    private void setupRecyclerView() {
        adapter = new UnivesitiesListAdapter(uniList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // Method to check if the device is connected to the internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Method to setup the try again button functionality
    private void setupTryAgainButton() {
        Button tryAgainButton = findViewById(R.id.button_try_again);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAgainClicked();
            }
        });
    }

    // Called when the try again button is clicked
    public void tryAgainClicked() {
        if (isNetworkAvailable()) {
            setContentView(R.layout.activity_main);
            initializeViews();
            populateUniversityList();
            setupRecyclerView();
            Toast.makeText(this, "Connected to the internet!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Still no internet connection. Please check your settings.", Toast.LENGTH_LONG).show();
        }
    }
}
