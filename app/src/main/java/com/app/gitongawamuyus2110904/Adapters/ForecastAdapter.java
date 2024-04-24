package com.app.gitongawamuyus2110904.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gitongawamuyus2110904.Models.ForecastModel;
import com.app.gitongawamuyus2110904.Utils.DateUtils;
import com.app.gitongawamuyus2110904.R;
import com.app.gitongawamuyus2110904.activities.DetailForecast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastModel> forecastList;

    Context context;


    public ForecastAdapter(List<ForecastModel> forecastList, Context context) {
        this.forecastList = forecastList;
        this.context = context;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_single_layout, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastModel forecast = forecastList.get(position);
        holder.dayTextView.setText(forecast.getDay());
        holder.minTextView.setText(forecast.getMin());
        holder.maxTextView.setText(forecast.getMax());



        Log.d("TAGsdfsdf", "onBindViewHolder: "+forecast);

        if (forecast.getMax().equals("")){
            holder.maxTextView.setText("NA");
        }

        String convertedDate = DateUtils.convertDateFormat(forecast.getDate());
        Log.d("TAGsdfsdf", "onBindViewHolder: "+convertedDate);
        if (position == 0) {
            holder.dateOneShow.setText(convertedDate);
        } else {
            String prevDate = forecastList.get(0).getDate(); // Get the date at position 0
            String nextDate = calculateNextDate(prevDate, position);
            Log.d("TAGdsdfsd", "onBindViewHolder: "+position +":" +nextDate);
            holder.dateOneShow.setText(nextDate);
        }





        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailForecast.class);
                intent.putExtra("forecastDetail", forecast);

                // Pass the date as well
                String date;
                if (position == 0) {
                    date = DateUtils.convertDateFormat(forecast.getDate());
                } else {
                    String prevDate = forecastList.get(0).getDate();
                    date = calculateNextDate(prevDate, position);
                }
                intent.putExtra("forecastDate", date);

                context.startActivity(intent);
            }
        });

        // Set image here if required
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView dayTextView;
        TextView dateOneShow;
        TextView minTextView;
        TextView maxTextView;
        ImageView imageView;
        CardView cardView;


        ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.DayOneShow);
            dateOneShow = itemView.findViewById(R.id.dateOneShow);
            minTextView = itemView.findViewById(R.id.MinONEShow);
            maxTextView = itemView.findViewById(R.id.MaxONEShow);
            imageView = itemView.findViewById(R.id.image_view_weather);
            cardView = itemView.findViewById(R.id.cardForecast);


        }
    }

    private String calculateNextDate(String previousDate, int position) {
        String nextDate = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
            Date date = inputFormat.parse(previousDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, position);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            nextDate = outputFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDate;
    }
}
