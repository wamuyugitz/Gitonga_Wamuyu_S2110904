package com.app.gitongawamuyus2110904.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.gitongawamuyus2110904.Adapters.ForecastAdapter;
import com.app.gitongawamuyus2110904.Models.ForecastModel;
import com.app.gitongawamuyus2110904.Utils.Constant;
import com.app.gitongawamuyus2110904.Utils.XmlParser;
import com.app.gitongawamuyus2110904.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// Activity for displaying three days forecast
public class ThreeDaysForcast extends AppCompatActivity {

    RecyclerView recyclerView; // RecyclerView to display forecast data
    ForecastAdapter forecastAdapter; // Adapter for populating the RecyclerView
    TextView textView; // TextView to display university location
    ImageView forecast_back; // ImageView for navigating back
    List<ForecastModel> forecastModels; // List to hold forecast models
    ProgressBar progressBar; // ProgressBar for indicating loading state
    ForecastModel forecastModel = null; // Forecast model instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_days_forcast);

        // Initialize views
        recyclerView = findViewById(R.id.recycler_view_Forecast);
        textView = findViewById(R.id.nameLocationForecast);
        forecast_back = findViewById(R.id.forecast_back);
        progressBar = findViewById(R.id.progressBar);

        // Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Set click listener for navigating back
        forecast_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Get university location from intent and set it to TextView
        Intent intent = getIntent();
        String universityLocation = intent.getStringExtra("UNIVERSITY_LOCATION");
        textView.setText(universityLocation);

        // Construct the API URL based on the location
        String apiUrl = getApiUrlForLocation(universityLocation);

        // Initialize forecast list and adapter
        forecastModels = new ArrayList<>();
        forecastAdapter = new ForecastAdapter(forecastModels,this);

        // Set layout manager and adapter for RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(forecastAdapter);

        // Execute AsyncTask to fetch weather data
        new FetchWeatherDataTask().execute(apiUrl);
    }

    // Method to construct API URL for given location
    private String getApiUrlForLocation(String location) {
        switch (location) {
            case "Glasgow":
                Constant.apiID ="2648579";
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
            case "London":
                Constant.apiID ="2643743";
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
            case "USA":
                Constant.apiID ="5308655";
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5308655";
            case "New York":
                Constant.apiID ="5128581";
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
            case "Oman":
                Constant.apiID ="287286";
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286";
            case "Mauritius":
                Constant.apiID ="934154";
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
            case "Bangladesh":
                Constant.apiID ="1185241";
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241";
            default:
                return "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/default";
        }
    }

    // AsyncTask to fetch weather data
    private class FetchWeatherDataTask extends AsyncTask<String, Void, List<ForecastModel>> {

        @Override
        protected List<ForecastModel> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            String url = urls[0];
            return fetchWeatherData(url);
        }

        @Override
        protected void onPostExecute(List<ForecastModel> forecastModels) {
            if (forecastModels != null) {
                updateUI(forecastModels);
            }
            progressBar.setVisibility(View.GONE);
        }

        // Method to fetch weather data
        private List<ForecastModel> fetchWeatherData(String urlString) {
            List<ForecastModel> forecastList = new ArrayList<>();
            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();

                // Call parseXml method of XmlParser class to parse XML data
                forecastList = XmlParser.parseXml(inputStream); // <- Here

            } catch (IOException | XmlPullParserException e) {
                Log.e("ThreeDaysForecast", "Error fetching or parsing data: " + e.getMessage());
            } finally {
                // Close resources
            }
            return forecastList;
        }

        // Method to extract date from XML parser
        private String extractDate(XmlPullParser parser) throws XmlPullParserException, IOException {
            String date = "";
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase("pubDate")) {
                    parser.next(); // Move to the text content
                    date = parser.getText().trim();
                    break;
                }
                eventType = parser.next();
            }
            return date;
        }
    }

    // Method to extract weather details from XML parser
    private void extractWeatherDetails(XmlPullParser parser, ForecastModel forecastModel) throws XmlPullParserException, IOException {
        String windSpeed = "";
        String windDirection = "";
        String visibility = "";
        String pressure = "";
        String humidity = "";
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.getName().equalsIgnoreCase("description")) {
                parser.next();
                String description = parser.getText().trim();
                // Splitting the description based on ", " to extract individual details
                String[] parts = description.split(", ");
                for (String part : parts) {
                    if (part.startsWith("Wind Speed:")) {
                        windSpeed = part.substring("Wind Speed: ".length());
                        forecastModel.setWindSpeed(windSpeed);
                    } else if (part.startsWith("Wind Direction:")) {
                        windDirection = part.substring("Wind Direction: ".length());
                        forecastModel.setWindDirection(windDirection);
                    } else if (part.startsWith("Visibility:")) {
                        visibility = part.substring("Visibility: ".length());
                        forecastModel.setVisibility(visibility);
                    } else if (part.startsWith("Pressure:")) {
                        pressure = part.substring("Pressure: ".length());
                        forecastModel.setPressure(pressure);
                    } else if (part.startsWith("Humidity:")) {
                        humidity = part.substring("Humidity: ".length());
                        forecastModel.setHumidity(humidity);
                    }
                }
                // Log weather details
                Log.d("umair", "Wind Speed: " + windSpeed);
                Log.d("umair", "Wind Direction: " + windDirection);
                Log.d("umair", "Visibility: " + visibility);
                Log.d("umair", "Pressure: " + pressure);
                Log.d("umair", "Humidity: " + humidity);
                break;
            }
            eventType = parser.next();
        }
    }

    // Method to update UI with forecast data
    private void updateUI(List<ForecastModel> forecastModels) {
        this.forecastModels.clear();
        this.forecastModels.addAll(forecastModels);
        forecastAdapter.notifyDataSetChanged();

        // Log forecast data
        for (ForecastModel model : forecastModels) {
            Log.d("ForecastData", "Day: " + model.getDay() + ", Min Temp: " + model.getMin() + ", Max Temp: " + model.getMax());
        }
    }
}