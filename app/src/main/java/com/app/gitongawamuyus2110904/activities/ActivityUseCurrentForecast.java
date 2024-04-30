/*
 * Author: Wamuyu Gitonga
 * Student ID: s2110904
 */
package com.app.gitongawamuyus2110904.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.gitongawamuyus2110904.R;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Activity for displaying current weather forecast details
public class ActivityUseCurrentForecast extends AppCompatActivity {
    ImageView detail_back;
    TextView DayOneShowDetail, detailTem, direction,DateOneShowDetail, speed, humidity, pressure, visibility, minTemp, maxTemp;

    ProgressBar progressBar;
    ScrollView linearLayout;

    // Called when the activity is starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_current_forecast);

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
        linearLayout = findViewById(R.id.linearLayout);
        progressBar = findViewById(R.id.progressBar);

        // Get intent data and fetch weather data
        Intent intent = getIntent();
        if (intent != null) {
            String apiId = intent.getStringExtra("apiID");
            String forecastDate = intent.getStringExtra("forecastDate"); // Extract date from intent extras

            if (apiId != null) {
                DateOneShowDetail.setText(forecastDate);
                String apiUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/" + apiId;
                new FetchWeatherDataTask().execute(apiUrl);
            }
        }

        // Set onClickListener for back button
        detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // AsyncTask to fetch weather data from the API in the background
    private class FetchWeatherDataTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... strings) {
            String url = strings[0];
            try {
                return Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Process fetched data and update UI
        @Override
        protected void onPostExecute(Document document) {
            if (document != null) {
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                Element item = document.select("item").first();
                if (item != null) {
                    String title = item.select("title").text();
                    extractMaxTemperatureFromTitle(title);

                    String description = item.select("description").text();

                    String temperature = extractValue(description, "Temperature: (.*?)°");
                    String windDirection = extractValue(description, "Wind Direction: (.*?),");
                    String windSpeed = extractValue(description, "Wind Speed: (.*?)mph");
                    String humiditydata = extractValue(description, "Humidity: (.*?)%");
                    String pressuredata = extractValue(description, "Pressure: (.*?)mb");
                    String visibilitydata = extractValue(description, "Visibility: (.*)");

                    // Set the extracted data to respective TextViews

                    Log.d("TAGdfgdfg", "temperature: " + temperature);
                    Log.d("TAGdfgdfg", "windDirection: " + windDirection);
                    Log.d("TAGdfgdfg", "windSpeed: " + windSpeed);
                    Log.d("TAGdfgdfg", "humiditydata: " + humiditydata);
                    Log.d("TAGdfgdfg", "pressuredata: " + pressuredata);
                    Log.d("TAGdfgdfg", "visibilitydata: " + visibilitydata);



//                    DayOneShowDetail.setText(title);
                    detailTem.setText(temperature+ "°");
                    if (windSpeed.trim().equals("--")) {
                        speed.setText("NA");
                    } else {
                        speed.setText(windSpeed);
                    }

                    if (humiditydata.trim().equals("--")) {
                        humidity.setText("NA");
                    } else {
                        humidity.setText(humiditydata);
                    }

                    if (visibilitydata.trim().equals("--")) {
                        visibility.setText("NA");
                    } else {
                        visibility.setText(visibilitydata);
                    }

                    if (pressuredata.trim().equals("--")) {
                        pressure.setText("NA");
                    } else {
                        pressure.setText(pressuredata);
                    }

                    if (windDirection.trim().equals("Direction not available")) {
                        direction.setText("NA");
                    } else {
                        direction.setText(windDirection);
                    }
                }
            }
        }
    }

    // Extracts max temperature from the title and displays it
    private void extractMaxTemperatureFromTitle(String title) {
        // Split the title by hyphen to get the day part
        String[] parts = title.split("-");
        if (parts.length > 0) {
            // Display only the first part which contains the day
            String dayPart = parts[0].trim();
            // If there's a comma, split it and get the first part
            if (dayPart.contains(",")) {
                DayOneShowDetail.setText(dayPart.split(",")[0].trim());
            } else {
                DayOneShowDetail.setText(dayPart);
            }
        }
    }

    // Extracts value from description using regex
    private String extractValue(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}