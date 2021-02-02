package com.example.holiday;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holiday.Retrofit.ApiClient;
import com.example.holiday.Retrofit.ApiInterface;
import com.example.holiday.Retrofit.City;
import com.example.holiday.Room.entity.Trips;
import com.google.android.material.slider.Slider;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTrip extends AppCompatActivity {

    private TextView startDate;
    private TextView endDate;


    private TextView  tripName;
    private TextView  destination;
    private RadioGroup radioGroup;
    private TextView slider;
    private RatingBar ratingBar;
    private TextView temp;
    private TextView humidity;
    private  TextView realFeel;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_trip);
        Intent intent=getIntent();

      int position=  intent.getIntExtra("trips",0);
        Trips trip=MainActivity.tripViewModel.getTrip(position);

        startDate= findViewById(R.id.dataStart);
        endDate=findViewById(R.id.dataEnd);
        tripName= findViewById(R.id.TripNameInput);
        destination=findViewById(R.id.DestinationInput);
        radioGroup=findViewById(R.id.radio);
        slider=findViewById(R.id.slider);
        ratingBar=findViewById(R.id.ratingBar);
        ratingBar.setStepSize(1.0f);
        ratingBar.setRating(trip.getRating());
       tripName.setText(trip.getTripName());
        destination.setText(trip.getDestination());
      slider.setText(trip.getPrice()+"$");
       startDate.setText(trip.getStartDate());
        endDate.setText(trip.getEndDate());
        temp=findViewById(R.id.TempInput);
        humidity=findViewById(R.id.HumidityInput);
        realFeel=findViewById(R.id.RealFeelInput);
        getWeatherTemp(trip.getDestination());
        //getWeatherData(trip.getDestination());
        RadioButton rbu1 =(RadioButton)findViewById(R.id.radio01);
        RadioButton rbu2 =(RadioButton)findViewById(R.id.radio02);
        RadioButton rbu3 =(RadioButton)findViewById(R.id.radio03);
        if(trip.getTripType().equals("City Break")){
            rbu1.setChecked(true);
        }
        else
        if(trip.getTripType().equals("Sea Side")){
            rbu2.setChecked(true);
        }
        else
        if(trip.getTripType().equals("Mountains")){
            rbu3.setChecked(true);
        }



    }
    private void getWeatherTemp(String name){
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<City> call=apiInterface.getWheatherData(name);
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if(apiInterface.getWheatherData(name)!=null) {
                    if((response.body() !=null)) {
                        temp.setText(response.body().getMain().getTemp());
                        humidity.setText(response.body().getMain().getHumidity());
                        realFeel.setText(response.body().getMain().getFeels_like());
                    }
                    else{
                        temp.setText("Unknown");
                        humidity.setText( "Unknown");
                        realFeel.setText( "Unknown");
                    }
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });


    }

}
