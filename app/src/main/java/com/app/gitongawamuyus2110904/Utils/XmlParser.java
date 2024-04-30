/*
 * Author: Wamuyu Gitonga
 * Student ID: s2110904
 */
package com.app.gitongawamuyus2110904.Utils;

import android.util.Log;

import com.app.gitongawamuyus2110904.Models.ForecastModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// Utility class for parsing XML data
public class XmlParser {

    // Method to parse XML data and extract forecast information
    public static List<ForecastModel> parseXml(InputStream inputStream) throws XmlPullParserException, IOException {
        List<ForecastModel> forecastList = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(inputStream, null);

        int eventType = parser.getEventType();
        ForecastModel forecastModel = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    // Start parsing when "item" tag is encountered
                    if (tagName.equalsIgnoreCase("item")) {
                        forecastModel = new ForecastModel();
                    } else if (forecastModel != null && tagName.equalsIgnoreCase("title")) {
                        // Extract weather details and set API ID, date, and day
                        String title = parser.nextText();
                        extractWeatherDetails(parser, forecastModel);
                        forecastModel.setApiId(Constant.apiID);
                        String day = extractDayFromTitle(title);
                        String date = extractDate(parser);
                        forecastModel.setDate(date);
                        forecastModel.setDay(day);
                        String minTemp = extractMinTemperatureFromTitle(title);
                        forecastModel.setMin(minTemp);
                        String maxTemp = extractMaxTemperatureFromTitle(title);
                        forecastModel.setMax(maxTemp);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    // End parsing when "item" tag is closed
                    if (tagName.equalsIgnoreCase("item")) {
                        if (forecastModel != null) {
                            forecastList.add(forecastModel);
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        return forecastList;
    }

    // Method to extract day from title string
    private static String extractDayFromTitle(String title) {
        String[] parts = title.split(":");
        if (parts.length > 0) {
            return parts[0].trim();
        } else {
            return ""; // Return empty string if unable to extract day
        }
    }

    // Method to extract minimum temperature from title string
    private static String extractMinTemperatureFromTitle(String title) {
        String[] parts = title.split(",");
        for (String part : parts) {
            if (part.contains("Minimum Temperature")) {
                String[] tempParts = part.split(":");
                if (tempParts.length > 1) {
                    String minTemp = tempParts[1].trim();
                    int endIndex = minTemp.indexOf("°");
                    if (endIndex != -1) {
                        return minTemp.substring(0, endIndex + 1);
                    }
                }
            }
        }
        return ""; // Return empty string if minimum temperature is not found
    }

    // Method to extract maximum temperature from title string
    private static String extractMaxTemperatureFromTitle(String title) {
        String[] parts = title.split(",");
        for (String part : parts) {
            if (part.contains("Maximum Temperature")) {
                String[] tempParts = part.split(":");
                if (tempParts.length > 2) {
                    String temp = tempParts[2].trim().replaceAll("°C", "");
                    return temp;
                }
            }
        }
        return ""; // Return empty string if maximum temperature is not found
    }

    // Method to extract weather details from XML parser
    private static void extractWeatherDetails(XmlPullParser parser, ForecastModel forecastModel) throws XmlPullParserException, IOException {
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

    // Method to extract date from XML parser
    private static String extractDate(XmlPullParser parser) throws XmlPullParserException, IOException {
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