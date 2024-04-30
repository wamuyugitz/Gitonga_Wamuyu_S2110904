/*
 * Author: Wamuyu Gitonga
 * Student ID: s2110904
 */
package com.app.gitongawamuyus2110904.Models;

import java.io.Serializable;


// Serializable class representing forecast data for a specific day
public class ForecastModel implements Serializable {
    // Fields representing forecast attributes
    String Day; // Day of the forecast
    String date; // Date of the forecast
    String Min; // Minimum temperature
    String Max; // Maximum temperature
    int image; // Image resource ID for weather icon

    // Additional weather details
    String windSpeed; // Wind speed
    String humidity; // Humidity level
    String pressure; // Atmospheric pressure
    String visibility; // Visibility range
    String windDirection; // Wind direction

    String ApiId; // API ID for location (optional)

    // Default constructor
    public ForecastModel() {
    }

    // Constructor with parameters
    public ForecastModel(String day, String date, String min, String max, int image) {
        Day = day;
        date = date;
        Min = min;
        Max = max;
        this.image = image;
    }

    // Getters and setters for forecast attributes
    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String name) {
        date = name;
    }

    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
    }

    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    // Getters and setters for additional weather details
    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getApiId() {
        return ApiId;
    }

    public void setApiId(String apiId) {
        ApiId = apiId;
    }

    // Override toString() method for debugging purposes
    @Override
    public String toString() {
        return "ForecastModel{" +
                "Day='" + Day + '\'' +
                ", date='" + date + '\'' +
                ", Min='" + Min + '\'' +
                ", Max='" + Max + '\'' +
                ", image=" + image +
                ", windSpeed='" + windSpeed + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", visibility='" + visibility + '\'' +
                ", windDirection='" + windDirection + '\'' +
                '}';
    }
}