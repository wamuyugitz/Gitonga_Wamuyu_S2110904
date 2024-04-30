/*
 * Author: Wamuyu Gitonga
 * Student ID: s2110904
 */
package com.app.gitongawamuyus2110904.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gitongawamuyus2110904.Models.ForecastModel;
import com.app.gitongawamuyus2110904.R;

// Activity for displaying detailed weather forecast
public class DetailForecast extends AppCompatActivity {
    ImageView detail_back;
    TextView DayOneShowDetail, detailTem, direction, speed, DateOneShowDetail,humidity, pressure, visibility, minTemp, maxTemp;
    AppCompatButton currentForcaste;

    // Called when the activity is starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_forecast);

        // Set window flags to enable full screen layout
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Initialize views
        detail_back = findViewById(R.id.detail_back);
        DayOneShowDetail = findViewById(R.id.DayOneShowDetail);
        DateOneShowDetail = findViewById(R.id.DateOneShowDetail);
        detailTem = findViewById(R.id.detailTem);
        direction = findViewById(R.id.direction);
        speed = findViewById(R.id.speed);
        humidity = findViewById(R.id.humiditi);
        pressure = findViewById(R.id.pressure);
        visibility = findViewById(R.id.visibleity);
        minTemp = findViewById(R.id.mindet);
        maxTemp = findViewById(R.id.maxdet);
        currentForcaste = findViewById(R.id.currentForcaste);

        // Set onClickListener for back button
        detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Extract forecast data from intent and populate views
        Intent intent = getIntent();
        if (intent != null) {
            ForecastModel forecast = (ForecastModel) intent.getSerializableExtra("forecastDetail");
            String forecastDate = intent.getStringExtra("forecastDate"); // Extract date from intent extras
            if (forecast != null && forecastDate != null) {
                // Populate views with forecast data
                DayOneShowDetail.setText(forecast.getDay());
                DateOneShowDetail.setText(forecastDate); // Set date to the TextView
                direction.setText(forecast.getWindDirection());
                speed.setText(forecast.getWindSpeed());
                humidity.setText(forecast.getHumidity());
                pressure.setText(forecast.getPressure());
                visibility.setText(forecast.getVisibility());
                // Check for null or empty strings in min and max temperature
                if (forecast.getMin() == null || forecast.getMin().isEmpty()) {
                    minTemp.setText("NA");
                } else {
                    minTemp.setText(forecast.getMin());
                }

                if (forecast.getMax() == null || forecast.getMax().isEmpty()) {
                    maxTemp.setText("NA");
                } else {
                    maxTemp.setText(forecast.getMax());
                }

                // Calculate average temperature and display
                if (forecast.getMin() != null && !forecast.getMin().isEmpty() && forecast.getMax() != null && !forecast.getMax().isEmpty()) {
                    String temperature = forecast.getMin();
                    String temperatureWithoutDegree = temperature.replaceAll("°", "");
                    int temperatureValue = Integer.parseInt(temperatureWithoutDegree);
                    String[] parts = forecast.getMax().split("\\D+");
                    int maxValue = Integer.parseInt(parts[0]);
                    int result = (temperatureValue + maxValue) / 2;
                    detailTem.setText(String.valueOf(result) + "°");
                } else {
                    detailTem.setText("NA");
                }


                // Set onClickListener for current forecast button
                currentForcaste.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Start ActivityUseCurrentForecast with API ID
                        Intent intent1 = new Intent(DetailForecast.this, ActivityUseCurrentForecast.class);
                        intent1.putExtra("apiID", forecast.getApiId());
                        intent1.putExtra("forecastDate", forecastDate); // Pass the forecast date
                        startActivity(intent1);
                    }
                });
            }
        }
    }
}