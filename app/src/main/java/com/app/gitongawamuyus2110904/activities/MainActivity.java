package com.app.gitongawamuyus2110904.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.app.gitongawamuyus2110904.Adapters.UnivesitiesListAdapter;
import com.app.gitongawamuyus2110904.Models.UniListModel;
import com.app.gitongawamuyus2110904.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


// MainActivity class responsible for displaying a list of universities
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView; // RecyclerView to display the list of universities
    FloatingActionButton floatingActionButton; // FloatingActionButton for adding new universities
    UnivesitiesListAdapter adapter; // Adapter for populating the RecyclerView
    List<UniListModel> uniList; // List of university models

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set activity to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Initialize views
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.fab_add);

        // Create dummy data for the list of universities
        uniList = new ArrayList<>();
        uniList.add(new UniListModel("GCU Campus", "USA"));
        uniList.add(new UniListModel("Glasgow Caledonian University", "Glasgow"));
        uniList.add(new UniListModel("GCU London", "London"));
        uniList.add(new UniListModel("GCNYC", "New York"));
        uniList.add(new UniListModel("Caledonian College Oman", "Oman"));
        uniList.add(new UniListModel("African Leadership Campus", "Mauritius"));
        uniList.add(new UniListModel("GCU Bangladesh", "Bangladesh"));

        // Initialize and set adapter for the RecyclerView
        adapter = new UnivesitiesListAdapter(uniList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Set click listener for FloatingActionButton to open MapsActivity
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
    }

    // Override onBackPressed method to handle back navigation
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}