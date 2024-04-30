/*
 * Author: Wamuyu Gitonga
 * Student ID: s2110904
 */
package com.app.gitongawamuyus2110904.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


// Utility class for date conversion
public class DateUtils {

    // Method to convert input date format to output date format
    public static String convertDateFormat(String inputDate) {
        String outputDate = "";
        try {
            // Define input date format
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            // Parse input date string
            Date date = inputFormat.parse(inputDate);

            // Define output date format
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            // Format parsed date to output date string
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }
}