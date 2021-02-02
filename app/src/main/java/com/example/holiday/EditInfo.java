package com.example.holiday;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.holiday.Room.entity.Trips;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;

public class EditInfo extends AppCompatActivity {
    private static final  String TAG="AddTrip";
    private TextView startDate;
    private TextView endDate;
    private DatePickerDialog.OnDateSetListener DataSetListener;
    private DatePickerDialog.OnDateSetListener DataSetListenerEnd;
    private MaterialButton btn;
    private EditText tripName;
    private EditText destination;
    private RadioGroup radioGroup;
    private Slider slider;
    private RatingBar ratingBar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_trip);
        Intent intentInit=getIntent();
        int position=  intentInit.getIntExtra("trips",0);
        Trips tripInitial=MainActivity.tripViewModel.getTrip(position);
        startDate=(TextView) findViewById(R.id.dataStart);
        startDate.setText(tripInitial.getStartDate());
        endDate=(TextView)findViewById(R.id.dataEnd);
        endDate.setText(tripInitial.getEndDate());
        tripName=(EditText) findViewById(R.id.TripNameInput);
        tripName.setText(tripInitial.getTripName());
        destination=(EditText)findViewById(R.id.DestinationInput);
        destination.setText(tripInitial.getDestination());
        radioGroup=(RadioGroup)findViewById(R.id.radio);
        slider=(Slider)findViewById(R.id.slider);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setStepSize(1.0f);
        ratingBar.setRating(tripInitial.getRating());
        startDate.setOnClickListener(view->{
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog,DataSetListener,year,month,day);
            dialog.show();
        });
        endDate.setOnClickListener(view->{
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog,DataSetListenerEnd,year,month,day);
            dialog.show();
        });

        DataSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date=month+"/"+dayOfMonth+"/"+year;
                startDate.setText(date);
            }
        };
        DataSetListenerEnd=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date=month+"/"+dayOfMonth+"/"+year;
                endDate.setText(date);
            }
        };
        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat currecny=NumberFormat.getCurrencyInstance();
                currecny.setCurrency(Currency.getInstance("USD"));
                return  currecny.format(value);
            }
        });
        btn=findViewById(R.id.btn);


        btn.setOnClickListener(view->{
            Trip trip=new Trip();
            trip.setTripName(tripName.getText().toString());
            trip.setDestination(destination.getText().toString());
            trip.setRatingBar(ratingBar.getNumStars());
            trip.setPrice((int)slider.getValue());
            int selectedId = 0;


            // find the radiobutton by returned id


            trip.setStartDate(startDate.getText().toString());
            trip.setEndDate(endDate.getText().toString());
            Intent intent=new Intent();
            if(radioGroup.getCheckedRadioButtonId()==-1)
            {
                Toast.makeText(this, "Please select a type", Toast.LENGTH_SHORT).show();
            }
            else {
                selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = (RadioButton)findViewById(selectedId);
                trip.setTripType(selectedRadioButton.getText().toString());
                intent.putExtra("trip", trip);
                intent.putExtra("pos", position);

                setResult(MainActivity.RESULT_OK, intent);
                finish();
            }
        });

    }
}
